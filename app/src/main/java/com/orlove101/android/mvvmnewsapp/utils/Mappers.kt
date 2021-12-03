package com.orlove101.android.mvvmnewsapp.utils

import com.orlove101.android.mvvmnewsapp.data.models.Article
import com.orlove101.android.mvvmnewsapp.domain.models.ArticleDomain

fun Article.mapToDomainArticle(): ArticleDomain {
    return ArticleDomain(
        id = id,
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = source,
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}

fun ArticleDomain.mapToArticle(): Article {
    return Article(
        id = id,
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = source,
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}

fun List<Article>.mapArticleListToArticleDomainList(): List<ArticleDomain> {
    val parsedList = mutableListOf<ArticleDomain>()

    for (article in this) {
        parsedList.add(article.mapToDomainArticle())
    }
    return parsedList
}