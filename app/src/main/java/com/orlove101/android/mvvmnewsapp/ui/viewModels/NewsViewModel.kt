package com.orlove101.android.mvvmnewsapp.ui.viewModels

import androidx.lifecycle.*
import androidx.paging.*
import com.orlove101.android.mvvmnewsapp.data.api.SavedNewsPageSource
import com.orlove101.android.mvvmnewsapp.data.repository.NewsRepositoryImpl
import com.orlove101.android.mvvmnewsapp.domain.models.*
import com.orlove101.android.mvvmnewsapp.domain.usecases.NewsUseCases
import com.orlove101.android.mvvmnewsapp.utils.PREFETCH_DISTANCE
import com.orlove101.android.mvvmnewsapp.utils.QUERY_PAGE_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "NewsViewModel"

// TODO tests

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepositoryImpl: NewsRepositoryImpl,
    private val newsUseCases: NewsUseCases
) : ViewModel() {
    val breakingNews: StateFlow<PagingData<ArticleDomain>> = Pager<Int, ArticleDomain>(
        PagingConfig(
            pageSize = QUERY_PAGE_SIZE,
            initialLoadSize = QUERY_PAGE_SIZE,
            prefetchDistance = PREFETCH_DISTANCE,
            enablePlaceholders = true
        )
    ) {
        newsRepositoryImpl.createBreakingNewsPageSource()
    }.flow
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    var currentSavedPagingSource: SavedNewsPageSource? = null

    val savedNews: StateFlow<PagingData<ArticleDomain>> = Pager<Int, ArticleDomain>(
        PagingConfig(
            pageSize = QUERY_PAGE_SIZE,
            initialLoadSize = QUERY_PAGE_SIZE,
            prefetchDistance = PREFETCH_DISTANCE,
            enablePlaceholders = true
        )
    ) {
        newsRepositoryImpl.createSavedNewsPageSource().also { currentSavedPagingSource = it }
    }.flow
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    val searchNews: StateFlow<PagingData<ArticleDomain>> = query
        .map {
            newSearchPager(it)
        }
        .flatMapLatest { pager -> pager.flow }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    private val newsEventsChannel = Channel<NewsEvent>()
    val newsEvent = newsEventsChannel.receiveAsFlow()

    fun saveArticle(article: ArticleDomain) = viewModelScope.launch {
        val params = SaveArticleParam(
            article = article,
            currentSavedPagingSource = currentSavedPagingSource
        )
        newsUseCases.saveArticleUseCase(saveArticleParam = params)
    }

    fun deleteArticle(article: ArticleDomain) = viewModelScope.launch {
        val params = DeleteArticleParam(
            article = article,
            currentSavedPagingSource = currentSavedPagingSource,
            newsEventsChannel = newsEventsChannel
        )
        newsUseCases.deleteArticleUseCase(deleteArticleParam = params)
    }

    fun onNewsSelected(article: ArticleDomain) {
        viewModelScope.launch {
            val param = NewsSelectedParam(
                newsEventsChannel = newsEventsChannel,
                article = article
            )
            newsUseCases.newsSelectedUseCase(param)
        }
    }

    private fun newSearchPager(query: String): Pager<Int, ArticleDomain> {
        return Pager(
            PagingConfig(
                pageSize = QUERY_PAGE_SIZE,
                initialLoadSize = QUERY_PAGE_SIZE,
                prefetchDistance = PREFETCH_DISTANCE,
                enablePlaceholders = true
            )) {
                newsRepositoryImpl.createEverythingNewsPageSource(query = query)
            }
    }

    fun setQuery(query: String) {
        val param = SaveQueryParams(_query = _query, query = query)

        newsUseCases.saveQueryUseCase(saveQueryParams = param)
    }

    sealed class NewsEvent {
        data class ShowToastMessage(val msgId: Int): NewsEvent()
        data class NavigateToArticleScreen(val article: ArticleDomain): NewsEvent()
        data class ShowArticleDeletedSnackbar(
            val msgId: Int,
            val actonMsgId: Int,
            val article: ArticleDomain
        ): NewsEvent()
    }
}
