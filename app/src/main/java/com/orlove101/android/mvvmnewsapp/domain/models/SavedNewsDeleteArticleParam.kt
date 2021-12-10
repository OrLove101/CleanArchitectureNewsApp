package com.orlove101.android.mvvmnewsapp.domain.models

import com.orlove101.android.mvvmnewsapp.ui.viewModels.SavedNewsViewModel
import kotlinx.coroutines.channels.Channel

data class SavedNewsDeleteArticleParam(
    val article: ArticleDomain,
    val newsEventsChannel: Channel<SavedNewsViewModel.SavedNewsEvent>,
)