package com.orlove101.android.mvvmnewsapp.domain.usecases

import com.orlove101.android.mvvmnewsapp.R
import com.orlove101.android.mvvmnewsapp.domain.models.SaveArticleParam
import com.orlove101.android.mvvmnewsapp.domain.repository.NewsRepository
import com.orlove101.android.mvvmnewsapp.ui.viewModels.NewsViewModel
import javax.inject.Inject

class SaveArticleUseCase @Inject constructor(
    private val newsRepositoryImpl: NewsRepository
) {

    suspend operator fun invoke(
        saveArticleParam: SaveArticleParam
    ) {
        newsRepositoryImpl.upsert(saveArticleParam.article)
        saveArticleParam.currentSavedPagingSource?.invalidate()
        saveArticleParam.newsEventsChannel.send(
            NewsViewModel.NewsEvent.ShowSnackbarWithoutAction(
                R.string.article_saved_snackbar_msg
            )
        )
    }
}