package com.orlove101.android.mvvmnewsapp.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.orlove101.android.mvvmnewsapp.domain.models.ArticleDomain
import com.orlove101.android.mvvmnewsapp.domain.models.EverythingNewsSelectedParam
import com.orlove101.android.mvvmnewsapp.domain.models.GetEverythingArticlesParam
import com.orlove101.android.mvvmnewsapp.domain.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EverythingNewsViewModel @Inject constructor(
    private val everythingNewsUseCases: EverythingNewsUseCases
) : ViewModel() {
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    val searchNews: StateFlow<PagingData<ArticleDomain>> = query
        .map { query ->
            val param = GetEverythingArticlesParam(query)

            everythingNewsUseCases.getEverythingArticlesUseCase(param)
        }
        .flatMapLatest { pager -> pager.flow }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    private val newsEventsChannel = Channel<EverythingNewsEvent>()
    val newsEvent = newsEventsChannel.receiveAsFlow()

    fun setQuery(query: String) {
        _query.tryEmit(query)
    }

    fun onNewsSelected(article: ArticleDomain) = viewModelScope.launch {
        val param = EverythingNewsSelectedParam(
            newsEventsChannel = newsEventsChannel,
            article = article
        )
        everythingNewsUseCases.newsSelectedUseCase(param)
    }

    sealed class EverythingNewsEvent {
        data class NavigateToArticleScreen(val article: ArticleDomain): EverythingNewsEvent()
    }
}