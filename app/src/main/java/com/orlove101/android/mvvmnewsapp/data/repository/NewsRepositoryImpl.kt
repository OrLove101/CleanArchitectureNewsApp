package com.orlove101.android.mvvmnewsapp.data.repository

import com.orlove101.android.mvvmnewsapp.data.api.BreakingNewsPageSource
import com.orlove101.android.mvvmnewsapp.data.api.EverythingNewsPageSource
import com.orlove101.android.mvvmnewsapp.data.api.NewsAPI
import com.orlove101.android.mvvmnewsapp.data.api.SavedNewsPageSource
import com.orlove101.android.mvvmnewsapp.data.db.ArticleDatabase
import com.orlove101.android.mvvmnewsapp.data.models.Article
import com.orlove101.android.mvvmnewsapp.domain.models.ArticleDomain
import com.orlove101.android.mvvmnewsapp.domain.repository.NewsRepository
import com.orlove101.android.mvvmnewsapp.utils.mapToArticle
import com.orlove101.android.mvvmnewsapp.utils.mapToDomainArticle
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImpl @Inject constructor(
    val db: ArticleDatabase,
    val api: NewsAPI
): NewsRepository {

    override suspend fun upsert(article: ArticleDomain): Long =
        db.getArticleDao().upsert(article.mapToArticle())

    override suspend fun deleteArticle(article: ArticleDomain) =
        db.getArticleDao().deleteArticle(article.mapToArticle())


    override fun createBreakingNewsPageSource() = BreakingNewsPageSource(
        newsApi = api
    )

    override fun createEverythingNewsPageSource(query: String) = EverythingNewsPageSource(
        newsApi = api,
        query = query
    )

    override fun createSavedNewsPageSource() = SavedNewsPageSource(
        db = db
    )
}
