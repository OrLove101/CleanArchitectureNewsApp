package com.orlove101.android.mvvmnewsapp.domain.models

import com.orlove101.android.mvvmnewsapp.data.api.SavedNewsPageSource

class SaveArticleParam (
    val article: ArticleDomain,
    val currentSavedPagingSource: SavedNewsPageSource?
)