package com.orlove101.android.mvvmnewsapp.data.api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.orlove101.android.mvvmnewsapp.data.models.NewsResponse
import com.orlove101.android.mvvmnewsapp.domain.models.ArticleDomain
import com.orlove101.android.mvvmnewsapp.utils.QUERY_PAGE_SIZE
import com.orlove101.android.mvvmnewsapp.utils.mapArticleListToArticleDomainList
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class BreakingNewsPageSource(
    private val newsApi: NewsAPI
): PagingSource<Int, ArticleDomain>() {

    override fun getRefreshKey(state: PagingState<Int, ArticleDomain>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleDomain> {
        val page: Int = params.key ?: 1

        return try {
            val response = newsApi.getBreakingNews(pageNumber = page)

            if (response.isSuccessful) {
                val articles = checkNotNull(response.body()).articles.toList()
                val nextKey = if (articles.size < QUERY_PAGE_SIZE) null else page + 1;
                val prevKey = if (page == 1) null else page - 1;

                LoadResult.Page(articles.mapArticleListToArticleDomainList(), prevKey, nextKey)
            }
            LoadResult.Error(HttpException(response))
        } catch (ex: IOException) {
            LoadResult.Error(ex)
        } catch (ex: HttpException) {
            LoadResult.Error(ex)
        }

    }
}
