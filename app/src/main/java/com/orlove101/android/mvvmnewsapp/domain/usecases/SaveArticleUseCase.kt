package com.orlove101.android.mvvmnewsapp.domain.usecases

import com.orlove101.android.mvvmnewsapp.domain.models.SaveArticleParam
import com.orlove101.android.mvvmnewsapp.domain.repository.NewsRepository
import javax.inject.Inject

class SaveArticleUseCase @Inject constructor(
    private val newsRepositoryImpl: NewsRepository
) {

    suspend operator fun invoke(
        saveArticleParam: SaveArticleParam
    ) {
        newsRepositoryImpl.upsert(saveArticleParam.article)
        saveArticleParam.currentSavedPagingSource?.invalidate()
    }
}