package com.orlove101.android.mvvmnewsapp.domain.repository

import com.orlove101.android.mvvmnewsapp.data.api.BreakingNewsPageSource
import com.orlove101.android.mvvmnewsapp.data.api.EverythingNewsPageSource
import com.orlove101.android.mvvmnewsapp.data.api.SavedNewsPageSource
import com.orlove101.android.mvvmnewsapp.domain.models.ArticleDomain

interface NewsRepository {
    suspend fun upsert(article: ArticleDomain): Long

    suspend fun deleteArticle(article: ArticleDomain)

    fun createBreakingNewsPageSource(): BreakingNewsPageSource

    fun createEverythingNewsPageSource(query: String): EverythingNewsPageSource

    fun createSavedNewsPageSource(): SavedNewsPageSource
}