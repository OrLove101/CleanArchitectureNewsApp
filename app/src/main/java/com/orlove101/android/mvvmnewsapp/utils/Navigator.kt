package com.orlove101.android.mvvmnewsapp.utils

import androidx.navigation.NavController
import com.orlove101.android.mvvmnewsapp.domain.models.ArticleDomain
import com.orlove101.android.mvvmnewsapp.ui.fragments.SearchNewsFragmentDirections

class Navigator(private val navController: NavController) {

    fun navigateToArticleScreen(article: ArticleDomain) {
        val action = SearchNewsFragmentDirections
            .actionSearchNewsFragmentToArticleFragment(article)
        navController.navigate(action)
    }
}