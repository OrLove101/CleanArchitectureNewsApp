package com.orlove101.android.mvvmnewsapp.domain.usecases

import javax.inject.Inject

data class EverythingNewsUseCases @Inject constructor(
    val newsSelectedUseCase: EverythingNewsSelectedUseCase,
    val getEverythingArticlesUseCase: GetEverythingArticlesUseCase
)