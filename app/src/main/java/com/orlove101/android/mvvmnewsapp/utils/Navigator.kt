package com.orlove101.android.mvvmnewsapp.utils

import androidx.navigation.NavController
import com.orlove101.android.mvvmnewsapp.domain.models.ArticleDomain
import com.orlove101.android.mvvmnewsapp.ui.fragments.BreakingNewsFragmentDirections
import com.orlove101.android.mvvmnewsapp.ui.fragments.SavedNewsFragmentDirections
import com.orlove101.android.mvvmnewsapp.ui.fragments.SearchNewsFragmentDirections

class Navigator(private val navController: NavController) {

    fun navigateFromSearchNewsToArticleScreen(article: ArticleDomain) {
        val action = SearchNewsFragmentDirections
            .actionSearchNewsFragmentToArticleFragment(article)
        navController.navigate(action)
    }

    fun navigateFromSavedNewsToArticleScreen(article: ArticleDomain) {
        val action = SavedNewsFragmentDirections
            .actionSavedNewsFragmentToArticleFragment(article)
        navController.navigate(action)
    }

    fun navigateFromBreakingNewsToArticleScreen(article: ArticleDomain) {
        val action = BreakingNewsFragmentDirections
            .actionBreakingNewsFragmentToArticleFragment(article)
        navController.navigate(action)
    }
}