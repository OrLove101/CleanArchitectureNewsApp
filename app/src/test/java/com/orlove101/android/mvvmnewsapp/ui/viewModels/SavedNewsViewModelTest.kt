package com.orlove101.android.mvvmnewsapp.ui.viewModels

import com.orlove101.android.mvvmnewsapp.data.repository.NewsRepositoryImpl
import com.orlove101.android.mvvmnewsapp.domain.models.ArticleDomain
import com.orlove101.android.mvvmnewsapp.domain.usecases.*
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import strikt.api.expectThat
import strikt.assertions.isA

@ExperimentalCoroutinesApi
object SavedNewsViewModelTest : Spek({
    val newsRepository by memoized {
        mockk<NewsRepositoryImpl>(relaxed = true)
    }
    val savedNewsViewModel by memoized {
        SavedNewsViewModel(
            savedNewsUseCases = SavedNewsUseCases(
                saveArticleUseCase = SavedNewsSaveArticleUseCase(newsRepository),
                savedNewsDeleteArticleUseCase = SavedNewsDeleteArticleUseCase(newsRepository),
                savedNewsSelectedUseCase = SavedNewsSelectedUseCase(),
                getSavedArticlesUseCase = GetSavedArticlesUseCase(newsRepository),
            )
        )
    }
    val article by memoized {
        mockk<ArticleDomain>(relaxed = true)
    }
    val testDispatcher = StandardTestDispatcher()

    beforeEachTest {
        Dispatchers.setMain(testDispatcher)
    }

    afterEachTest {
        Dispatchers.resetMain()
    }

    describe("Saved News view model test") {
        describe("Click on news") {
            it("Should navigate to article screen") {
                savedNewsViewModel.onSavedNewsSelected(article).invokeOnCompletion {
                    runTest {
                        expectThat(savedNewsViewModel.newsEvent.last())
                            .isA<SavedNewsViewModel.SavedNewsEvent.NavigateToArticleScreen>()
                    }
                }
            }
        }
        describe("Delete news") {
            it("Should show snackbar with undo action") {
                savedNewsViewModel.deleteArticle(article).invokeOnCompletion {
                    runTest {
                        expectThat(expectThat(savedNewsViewModel.newsEvent.last()))
                            .isA<SavedNewsViewModel.SavedNewsEvent.ShowArticleDeletedSnackbar>()
                    }
                }
            }
        }
    }
})