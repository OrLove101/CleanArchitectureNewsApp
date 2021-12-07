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
import strikt.assertions.isEqualTo

@ExperimentalCoroutinesApi
object NewsViewModelTest : Spek({
    val newsRepository by memoized {
        mockk<NewsRepositoryImpl>(relaxed = true)
    }
    val viewModel by memoized {
        NewsViewModel(
            newsRepositoryImpl =  newsRepository,
            newsUseCases = NewsUseCases(
                deleteArticleUseCase = DeleteArticleUseCase(newsRepository),
                newsSelectedUseCase = NewsSelectedUseCase(),
                saveArticleUseCase = SaveArticleUseCase(newsRepository),
                saveQueryUseCase = SaveQueryUseCase()
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

    describe("News view model test") {
        describe("Click on news") {
            it("Should navigate to article screen") {
                viewModel.onNewsSelected(article).invokeOnCompletion {
                    runTest {
                        expectThat(viewModel.newsEvent.last())
                            .isA<NewsViewModel.NewsEvent.NavigateToArticleScreen>()
                    }
                }
            }
        }
        describe("Delete news") {
            it("Should show snackbar with undo action") {
                viewModel.deleteArticle(article).invokeOnCompletion {
                    runTest {
                        expectThat(expectThat(viewModel.newsEvent.last()))
                                .isA<NewsViewModel.NewsEvent.ShowArticleDeletedSnackbar>()
                    }
                }
            }
        }
    }
})