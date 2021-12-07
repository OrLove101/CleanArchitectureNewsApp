package com.orlove101.android.mvvmnewsapp.ui.viewModels

import androidx.lifecycle.*
import androidx.paging.*
import com.orlove101.android.mvvmnewsapp.data.api.SavedNewsPageSource
import com.orlove101.android.mvvmnewsapp.data.repository.NewsRepositoryImpl
import com.orlove101.android.mvvmnewsapp.domain.models.*
import com.orlove101.android.mvvmnewsapp.domain.usecases.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepositoryImpl: NewsRepositoryImpl,
    private val newsUseCases: NewsUseCases
) : ViewModel() {
    val breakingNews: StateFlow<PagingData<ArticleDomain>> = newsRepositoryImpl.getBreakingNews()
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    private var currentSavedPagingSource: SavedNewsPageSource? = null

    val savedNews: StateFlow<PagingData<ArticleDomain>> = newsRepositoryImpl.getSavedNews()
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    val searchNews: StateFlow<PagingData<ArticleDomain>> = query
        .map {
            newsRepositoryImpl.getEverythingNewsPager(it)
        }
        .flatMapLatest { pager -> pager.flow }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    private val newsEventsChannel = Channel<NewsEvent>()
    val newsEvent = newsEventsChannel.receiveAsFlow()

    fun saveArticle(article: ArticleDomain) = viewModelScope.launch {
        val params = SaveArticleParam(
            article = article,
            currentSavedPagingSource = currentSavedPagingSource,
            newsEventsChannel = newsEventsChannel
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

    fun onNewsSelected(article: ArticleDomain) = viewModelScope.launch {
        val param = NewsSelectedParam(
            newsEventsChannel = newsEventsChannel,
            article = article
        )
        newsUseCases.newsSelectedUseCase(param)
    }

    fun setQuery(query: String) {
        val param = SaveQueryParams(_query = _query, query = query)

        newsUseCases.saveQueryUseCase(saveQueryParams = param)
    }

    sealed class NewsEvent {
        data class NavigateToArticleScreen(val article: ArticleDomain): NewsEvent()
        data class ShowArticleDeletedSnackbar(
            val msgId: Int,
            val actonMsgId: Int,
            val article: ArticleDomain
        ): NewsEvent()
        data class ShowSnackbarWithoutAction(
            val msgId: Int
        ): NewsEvent()
    }
}
