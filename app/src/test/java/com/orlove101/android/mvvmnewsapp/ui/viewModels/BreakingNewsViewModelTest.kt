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
object BreakingNewsViewModelTest : Spek({
    val newsRepository by memoized {
        mockk<NewsRepositoryImpl>(relaxed = true)
    }
    val breakingNewsViewModel by memoized {
        BreakingNewsViewModel(
            breakingNewsUseCases = BreakingNewsUseCases(
                newsSelectedUseCase = BreakingNewsSelectedUseCase(),
                getBreakingArticlesUseCase = GetBreakingArticlesUseCase(newsRepository)
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

    describe("Breaking News view model test") {
        describe("Click on news") {
            it("Should navigate to article screen") {
                breakingNewsViewModel.onNewsSelected(article).invokeOnCompletion {
                    runTest {
                        expectThat(breakingNewsViewModel.newsEvent.last())
                            .isA<BreakingNewsViewModel.BreakingNewsEvent.NavigateToArticleScreen>()
                    }
                }
            }
        }
    }
})