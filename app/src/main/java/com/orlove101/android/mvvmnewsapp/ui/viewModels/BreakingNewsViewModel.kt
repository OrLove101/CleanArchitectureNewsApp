package com.orlove101.android.mvvmnewsapp.ui.viewModels

import androidx.lifecycle.*
import androidx.paging.*
import com.orlove101.android.mvvmnewsapp.domain.models.*
import com.orlove101.android.mvvmnewsapp.domain.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreakingNewsViewModel @Inject constructor(
    private val breakingNewsUseCases: BreakingNewsUseCases
) : ViewModel() {
    val breakingNews: StateFlow<PagingData<ArticleDomain>> = breakingNewsUseCases.getBreakingArticlesUseCase()
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    private val newsEventsChannel = Channel<BreakingNewsEvent>()
    val newsEvent = newsEventsChannel.receiveAsFlow()

    fun onNewsSelected(article: ArticleDomain) = viewModelScope.launch {
        val param = BreakingNewsSelectedParam(
            breakingBreakingNewsEventsChannel = newsEventsChannel,
            article = article
        )
        breakingNewsUseCases.newsSelectedUseCase(param)
    }

    sealed class BreakingNewsEvent {
        data class NavigateToArticleScreen(val article: ArticleDomain): BreakingNewsEvent()
    }
}
