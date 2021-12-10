package com.orlove101.android.mvvmnewsapp.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orlove101.android.mvvmnewsapp.domain.models.ArticleDomain
import com.orlove101.android.mvvmnewsapp.domain.models.ArticleSaveArticleParam
import com.orlove101.android.mvvmnewsapp.domain.usecases.ArticleUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val articleUseCases: ArticleUseCases
) : ViewModel() {
    private val newsEventsChannel = Channel<ArticleEvent>()
    val newsEvent = newsEventsChannel.receiveAsFlow()

    fun saveArticle(article: ArticleDomain) = viewModelScope.launch {
        val params = ArticleSaveArticleParam(
            article = article,
            newsEventsChannel = newsEventsChannel
        )
        articleUseCases.articleSaveArticleUseCase(articleSaveArticleParam = params)
    }

    sealed class ArticleEvent {
        data class ShowSnackbarWithoutAction(
            val msgId: Int
        ): ArticleEvent()
    }
}