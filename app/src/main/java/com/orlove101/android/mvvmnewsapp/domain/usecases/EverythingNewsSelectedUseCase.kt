package com.orlove101.android.mvvmnewsapp.domain.usecases

import com.orlove101.android.mvvmnewsapp.domain.models.EverythingNewsSelectedParam
import com.orlove101.android.mvvmnewsapp.ui.viewModels.EverythingNewsViewModel
import javax.inject.Inject

class EverythingNewsSelectedUseCase @Inject constructor() {

    suspend operator fun invoke(
        newsSelectedParam: EverythingNewsSelectedParam
    ) {
        newsSelectedParam.newsEventsChannel.send(
            EverythingNewsViewModel.EverythingNewsEvent.NavigateToArticleScreen(
                newsSelectedParam.article
            )
        )
    }
}