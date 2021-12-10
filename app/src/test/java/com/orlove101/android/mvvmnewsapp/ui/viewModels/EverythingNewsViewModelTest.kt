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
object EverythingNewsViewModelTest : Spek({
    val newsRepository by memoized {
        mockk<NewsRepositoryImpl>(relaxed = true)
    }
    val everythingNewsViewModel by memoized {
        EverythingNewsViewModel(
            everythingNewsUseCases = EverythingNewsUseCases(
                newsSelectedUseCase = EverythingNewsSelectedUseCase(),
                getEverythingArticlesUseCase = GetEverythingArticlesUseCase(newsRepository)
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

    describe("Everything News view model test") {
        describe("Click on news") {
            it("Should navigate to article screen") {
                everythingNewsViewModel.onNewsSelected(article).invokeOnCompletion {
                    runTest {
                        expectThat(everythingNewsViewModel.newsEvent.last())
                            .isA<EverythingNewsViewModel.EverythingNewsEvent.NavigateToArticleScreen>()
                    }
                }
            }
        }
    }
})