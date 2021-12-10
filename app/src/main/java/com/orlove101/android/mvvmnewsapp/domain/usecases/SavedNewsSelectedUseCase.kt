package com.orlove101.android.mvvmnewsapp.domain.usecases

import com.orlove101.android.mvvmnewsapp.domain.models.SavedNewsSelectedParam
import com.orlove101.android.mvvmnewsapp.ui.viewModels.SavedNewsViewModel
import javax.inject.Inject

class SavedNewsSelectedUseCase @Inject constructor() {

    suspend operator fun invoke(
        newsSelectedParam: SavedNewsSelectedParam
    ) {
        newsSelectedParam.newsEventsChannel.send(
            SavedNewsViewModel.SavedNewsEvent.NavigateToArticleScreen(
                newsSelectedParam.article
            )
        )
    }
}