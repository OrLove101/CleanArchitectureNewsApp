package com.orlove101.android.mvvmnewsapp.data.api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.orlove101.android.mvvmnewsapp.data.db.ArticleDatabase
import com.orlove101.android.mvvmnewsapp.domain.models.ArticleDomain
import com.orlove101.android.mvvmnewsapp.utils.QUERY_PAGE_SIZE
import com.orlove101.android.mvvmnewsapp.utils.mapArticleListToArticleDomainList
import kotlinx.coroutines.flow.first

class SavedNewsPageSource(
    private val db: ArticleDatabase
): PagingSource<Int, ArticleDomain>() {

    override fun getRefreshKey(state: PagingState<Int, ArticleDomain>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleDomain> {
        val page: Int = params.key ?: 1
        val getFrom: Int = page.minus(1) * 20
        val articles = db
            .getArticleDao()
            .getArticles(
                getFrom = getFrom,
                pageSize = QUERY_PAGE_SIZE
            )
            .first()
        val nextKey = if (articles.size < QUERY_PAGE_SIZE) null else page + 1;
        val prevKey = if (page == 1) null else page - 1;

        return LoadResult.Page(articles.mapArticleListToArticleDomainList(), prevKey, nextKey)
    }
}