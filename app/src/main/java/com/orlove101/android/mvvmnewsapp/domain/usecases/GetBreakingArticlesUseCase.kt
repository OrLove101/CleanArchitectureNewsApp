package com.orlove101.android.mvvmnewsapp.domain.usecases

import androidx.paging.PagingData
import com.orlove101.android.mvvmnewsapp.domain.models.ArticleDomain
import com.orlove101.android.mvvmnewsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBreakingArticlesUseCase @Inject constructor(
    private val newsRepositoryImpl: NewsRepository
) {

    operator fun invoke(): Flow<PagingData<ArticleDomain>> {
        return newsRepositoryImpl.getBreakingNews()
    }
}