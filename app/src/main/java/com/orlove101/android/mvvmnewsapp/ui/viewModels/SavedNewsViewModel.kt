package com.orlove101.android.mvvmnewsapp.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.orlove101.android.mvvmnewsapp.domain.models.*
import com.orlove101.android.mvvmnewsapp.domain.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedNewsViewModel @Inject constructor(
    private val savedNewsUseCases: SavedNewsUseCases
) : ViewModel() {
    val savedNews: StateFlow<PagingData<ArticleDomain>> = savedNewsUseCases.getSavedArticlesUseCase()
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    private val newsEventsChannel = Channel<SavedNewsEvent>()
    val newsEvent = newsEventsChannel.receiveAsFlow()

    fun deleteArticle(article: ArticleDomain) = viewModelScope.launch {
        val params = SavedNewsDeleteArticleParam(
            article = article,
            newsEventsChannel = newsEventsChannel
        )
        savedNewsUseCases.savedNewsDeleteArticleUseCase(savedNewsDeleteArticleParam = params)
    }

    fun onSavedNewsSelected(article: ArticleDomain) = viewModelScope.launch {
        val param = SavedNewsSelectedParam(
            newsEventsChannel = newsEventsChannel,
            article = article
        )
        savedNewsUseCases.savedNewsSelectedUseCase(param)
    }

    fun saveArticle(article: ArticleDomain) = viewModelScope.launch {
        val params = SavedNewsSaveArticleParam(
            article = article,
            newsEventsChannel = newsEventsChannel
        )
        savedNewsUseCases.saveArticleUseCase(saveArticleParam = params)
    }

    sealed class SavedNewsEvent {
        data class NavigateToArticleScreen(val article: ArticleDomain): SavedNewsEvent()
        data class ShowArticleDeletedSnackbar(
            val msgId: Int,
            val actonMsgId: Int,
            val article: ArticleDomain
        ): SavedNewsEvent()
        data class ShowSnackbarWithoutAction(
            val msgId: Int
        ): SavedNewsEvent()
    }
}