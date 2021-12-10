package com.orlove101.android.mvvmnewsapp.domain.usecases

import javax.inject.Inject

data class BreakingNewsUseCases @Inject constructor(
    val newsSelectedUseCase: BreakingNewsSelectedUseCase,
    val getBreakingArticlesUseCase: GetBreakingArticlesUseCase,
)