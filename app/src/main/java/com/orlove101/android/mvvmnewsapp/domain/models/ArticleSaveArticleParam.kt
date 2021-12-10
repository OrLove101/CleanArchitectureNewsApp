package com.orlove101.android.mvvmnewsapp.domain.models

import com.orlove101.android.mvvmnewsapp.ui.viewModels.ArticleViewModel
import kotlinx.coroutines.channels.Channel

class ArticleSaveArticleParam (
    val article: ArticleDomain,
    val newsEventsChannel: Channel<ArticleViewModel.ArticleEvent>,
)