package com.orlove101.android.mvvmnewsapp.domain.usecases

import com.orlove101.android.mvvmnewsapp.R
import com.orlove101.android.mvvmnewsapp.domain.models.ArticleSaveArticleParam
import com.orlove101.android.mvvmnewsapp.domain.repository.NewsRepository
import com.orlove101.android.mvvmnewsapp.ui.viewModels.ArticleViewModel
import javax.inject.Inject

class ArticleSaveArticleUseCase @Inject constructor(
    private val newsRepositoryImpl: NewsRepository
) {

    suspend operator fun invoke(
        articleSaveArticleParam: ArticleSaveArticleParam
    ) {
        newsRepositoryImpl.upsert(articleSaveArticleParam.article)
        newsRepositoryImpl.getSavedDataSource()?.invalidate()
        articleSaveArticleParam.newsEventsChannel.send(
            ArticleViewModel.ArticleEvent.ShowSnackbarWithoutAction(
                R.string.article_saved_snackbar_msg
            )
        )
    }
}