package com.orlove101.android.mvvmnewsapp.domain.models

import com.orlove101.android.mvvmnewsapp.ui.viewModels.EverythingNewsViewModel
import kotlinx.coroutines.channels.Channel

class EverythingNewsSelectedParam(
    val newsEventsChannel: Channel<EverythingNewsViewModel.EverythingNewsEvent>,
    val article: ArticleDomain
)