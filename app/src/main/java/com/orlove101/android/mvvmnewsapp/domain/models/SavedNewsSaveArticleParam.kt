package com.orlove101.android.mvvmnewsapp.domain.models

import com.orlove101.android.mvvmnewsapp.ui.viewModels.SavedNewsViewModel
import kotlinx.coroutines.channels.Channel

class SavedNewsSaveArticleParam (
    val article: ArticleDomain,
    val newsEventsChannel: Channel<SavedNewsViewModel.SavedNewsEvent>
)