package com.orlove101.android.mvvmnewsapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.orlove101.android.mvvmnewsapp.databinding.ItemArticlePreviewBinding
import com.orlove101.android.mvvmnewsapp.domain.models.ArticleDomain

class NewsAdapter: PagingDataAdapter<ArticleDomain, NewsAdapter.ArticleViewHolder>(ArticleDifferCallback) {

    inner class ArticleViewHolder(private val binding: ItemArticlePreviewBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ArticleDomain?) {
            item?.let {
                binding.apply {
                    Glide.with(root).load(item.urlToImage).into(ivArticleImage)
                    tvSource.text = item.source?.name
                    tvTitle.text = item.title
                    tvDescription.text = item.description
                    tvPublishedAt.text = item.publishedAt
                    tvTitle.setOnClickListener {
                        onItemClickListener?.let {
                            it(item)
                        }
                    }
                    tvDescription.setOnClickListener {
                        onItemClickListener?.let {
                            it(item)
                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsAdapter.ArticleViewHolder {
        val binding = ItemArticlePreviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsAdapter.ArticleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private var onItemClickListener: ((ArticleDomain) -> Unit)? = null

    fun setOnItemClickListener(listener: (ArticleDomain) -> Unit) {
        onItemClickListener = listener
    }
}

private object ArticleDifferCallback: DiffUtil.ItemCallback<ArticleDomain>() {

    override fun areItemsTheSame(oldItem: ArticleDomain, newItem: ArticleDomain): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: ArticleDomain, newItem: ArticleDomain): Boolean {
        return oldItem == newItem
    }
}
