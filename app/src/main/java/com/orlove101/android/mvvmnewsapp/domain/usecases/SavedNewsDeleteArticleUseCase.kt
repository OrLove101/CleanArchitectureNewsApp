package com.orlove101.android.mvvmnewsapp.domain.usecases

import com.orlove101.android.mvvmnewsapp.R
import com.orlove101.android.mvvmnewsapp.domain.models.SavedNewsDeleteArticleParam
import com.orlove101.android.mvvmnewsapp.domain.repository.NewsRepository
import com.orlove101.android.mvvmnewsapp.ui.viewModels.SavedNewsViewModel
import javax.inject.Inject

class SavedNewsDeleteArticleUseCase @Inject constructor(
    private val newsRepositoryImpl: NewsRepository
) {

    suspend operator fun invoke(
        savedNewsDeleteArticleParam: SavedNewsDeleteArticleParam
    ) {
        newsRepositoryImpl.deleteArticle(savedNewsDeleteArticleParam.article)
        newsRepositoryImpl.getSavedDataSource()?.invalidate()
        savedNewsDeleteArticleParam.newsEventsChannel.send(
            SavedNewsViewModel.SavedNewsEvent.ShowArticleDeletedSnackbar(
                R.string.delete_article_snackbar_msg,
                R.string.delete_article_snackbar_action,
                savedNewsDeleteArticleParam.article
            )
        )
    }
}