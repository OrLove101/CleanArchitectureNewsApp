package com.orlove101.android.mvvmnewsapp.domain.usecases

import androidx.paging.Pager
import com.orlove101.android.mvvmnewsapp.domain.models.ArticleDomain
import com.orlove101.android.mvvmnewsapp.domain.models.GetEverythingArticlesParam
import com.orlove101.android.mvvmnewsapp.domain.repository.NewsRepository
import javax.inject.Inject

class GetEverythingArticlesUseCase @Inject constructor(
    private val newsRepositoryImpl: NewsRepository
) {

    operator fun invoke(
        getEverythingArticlesParam: GetEverythingArticlesParam
    ): Pager<Int, ArticleDomain> {
        return newsRepositoryImpl.getEverythingNewsPager(getEverythingArticlesParam.query)
    }
}