package com.orlove101.android.mvvmnewsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.orlove101.android.mvvmnewsapp.data.api.BreakingNewsPageSource
import com.orlove101.android.mvvmnewsapp.data.api.EverythingNewsPageSource
import com.orlove101.android.mvvmnewsapp.data.api.NewsAPI
import com.orlove101.android.mvvmnewsapp.data.api.SavedNewsPageSource
import com.orlove101.android.mvvmnewsapp.data.db.ArticleDatabase
import com.orlove101.android.mvvmnewsapp.domain.models.ArticleDomain
import com.orlove101.android.mvvmnewsapp.domain.repository.NewsRepository
import com.orlove101.android.mvvmnewsapp.utils.PREFETCH_DISTANCE
import com.orlove101.android.mvvmnewsapp.utils.QUERY_PAGE_SIZE
import com.orlove101.android.mvvmnewsapp.utils.mapToArticle
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImpl @Inject constructor(
    val db: ArticleDatabase,
    val api: NewsAPI
): NewsRepository {
    private var currentSavedDataSource: SavedNewsPageSource? = null

    override fun getSavedDataSource() = currentSavedDataSource

    override suspend fun upsert(article: ArticleDomain): Long =
        db.getArticleDao().upsert(article.mapToArticle())

    override suspend fun deleteArticle(article: ArticleDomain) =
        db.getArticleDao().deleteArticle(article.mapToArticle())


    override fun getBreakingNews() = Pager<Int, ArticleDomain>(
        PagingConfig(
            pageSize = QUERY_PAGE_SIZE,
            initialLoadSize = QUERY_PAGE_SIZE,
            prefetchDistance = PREFETCH_DISTANCE,
            enablePlaceholders = true
        )
    ) {
        BreakingNewsPageSource(newsApi = api)
    }.flow

    override fun getEverythingNewsPager(query: String) = Pager(
        PagingConfig(
            pageSize = QUERY_PAGE_SIZE,
            initialLoadSize = QUERY_PAGE_SIZE,
            prefetchDistance = PREFETCH_DISTANCE,
            enablePlaceholders = true
        )) {
            EverythingNewsPageSource(
                newsApi = api,
                query = query
            )
        }



    override fun getSavedNews() = Pager<Int, ArticleDomain>(
            PagingConfig(
                pageSize = QUERY_PAGE_SIZE,
                initialLoadSize = QUERY_PAGE_SIZE,
                prefetchDistance = PREFETCH_DISTANCE,
                enablePlaceholders = true
            )
        ) {
            SavedNewsPageSource(db = db).also {
                currentSavedDataSource = it
            }
        }.flow

}
