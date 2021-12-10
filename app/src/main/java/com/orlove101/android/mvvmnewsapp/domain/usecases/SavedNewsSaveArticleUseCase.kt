package com.orlove101.android.mvvmnewsapp.domain.usecases

import com.orlove101.android.mvvmnewsapp.R
import com.orlove101.android.mvvmnewsapp.domain.models.SavedNewsSaveArticleParam
import com.orlove101.android.mvvmnewsapp.domain.repository.NewsRepository
import com.orlove101.android.mvvmnewsapp.ui.viewModels.SavedNewsViewModel
import javax.inject.Inject

class SavedNewsSaveArticleUseCase @Inject constructor(
    private val newsRepositoryImpl: NewsRepository
) {

    suspend operator fun invoke(
        saveArticleParam: SavedNewsSaveArticleParam
    ) {
        newsRepositoryImpl.upsert(saveArticleParam.article)
        newsRepositoryImpl.getSavedDataSource()?.invalidate()
        saveArticleParam.newsEventsChannel.send(
            SavedNewsViewModel.SavedNewsEvent.ShowSnackbarWithoutAction(
                R.string.article_saved_snackbar_msg
            )
        )
    }
}