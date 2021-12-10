package com.orlove101.android.mvvmnewsapp.domain.usecases

import javax.inject.Inject

data class SavedNewsUseCases @Inject constructor (
    val saveArticleUseCase: SavedNewsSaveArticleUseCase,
    val savedNewsDeleteArticleUseCase: SavedNewsDeleteArticleUseCase,
    val savedNewsSelectedUseCase: SavedNewsSelectedUseCase,
    val getSavedArticlesUseCase: GetSavedArticlesUseCase,
)