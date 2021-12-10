package com.orlove101.android.mvvmnewsapp.domain.usecases

import javax.inject.Inject

data class ArticleUseCases @Inject constructor(
    val articleSaveArticleUseCase: ArticleSaveArticleUseCase,
)