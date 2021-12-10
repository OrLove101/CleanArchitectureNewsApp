package com.orlove101.android.mvvmnewsapp.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.orlove101.android.mvvmnewsapp.data.api.SavedNewsPageSource
import com.orlove101.android.mvvmnewsapp.domain.models.ArticleDomain
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun upsert(article: ArticleDomain): Long

    suspend fun deleteArticle(article: ArticleDomain)

    fun getBreakingNews(): Flow<PagingData<ArticleDomain>>

    fun getEverythingNewsPager(query: String): Pager<Int, ArticleDomain>

    fun getSavedNews():  Flow<PagingData<ArticleDomain>>

    fun getSavedDataSource(): SavedNewsPageSource?
}