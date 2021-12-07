package com.orlove101.android.mvvmnewsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.orlove101.android.mvvmnewsapp.databinding.FragmentArticleBinding
import com.orlove101.android.mvvmnewsapp.ui.viewModels.NewsViewModel
import com.orlove101.android.mvvmnewsapp.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ArticleFragment: Fragment() {
    private var binding by autoCleared<FragmentArticleBinding>()
    private val viewModel: NewsViewModel by viewModels()
    val args: ArticleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsEventHandler()

        val article = args.article

        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url.toString())
        }

        binding.fab.setOnClickListener {
            viewModel.saveArticle(article)
        }
    }

    private fun newsEventHandler() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.newsEvent.collectLatest { event ->
                when(event) {
                    is NewsViewModel.NewsEvent.ShowSnackbarWithoutAction -> {
                        Snackbar.make(binding.root, getString(event.msgId), Snackbar.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
    }
}