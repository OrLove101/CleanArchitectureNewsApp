package com.orlove101.android.mvvmnewsapp.domain.models

import com.orlove101.android.mvvmnewsapp.ui.viewModels.BreakingNewsViewModel
import kotlinx.coroutines.channels.Channel

data class BreakingNewsSelectedParam(
    val breakingBreakingNewsEventsChannel: Channel<BreakingNewsViewModel.BreakingNewsEvent>,
    val article: ArticleDomain
)
