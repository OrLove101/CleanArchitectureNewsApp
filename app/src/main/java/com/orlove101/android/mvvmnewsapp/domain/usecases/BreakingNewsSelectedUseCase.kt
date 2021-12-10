package com.orlove101.android.mvvmnewsapp.domain.usecases

import com.orlove101.android.mvvmnewsapp.domain.models.BreakingNewsSelectedParam
import com.orlove101.android.mvvmnewsapp.ui.viewModels.BreakingNewsViewModel
import javax.inject.Inject

class BreakingNewsSelectedUseCase @Inject constructor() {

    suspend operator fun invoke(
        newsSelectedParam: BreakingNewsSelectedParam
    ) {
        newsSelectedParam.breakingBreakingNewsEventsChannel.send(
            BreakingNewsViewModel.BreakingNewsEvent.NavigateToArticleScreen(
                newsSelectedParam.article
            )
        )
    }
}