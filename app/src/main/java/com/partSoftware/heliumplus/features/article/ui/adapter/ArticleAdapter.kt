package com.partSoftware.heliumplus.features.article.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.partSoftware.heliumplus.databinding.ItemArticleListBinding
import com.partSoftware.heliumplus.features.article.ui.model.ArticleView

class ArticleAdapter(
    val isShowAuthor: Boolean = true
) :
    ListAdapter<ArticleView, ArticleAdapter.ViewHolder>(DiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemArticleListBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    private var onItemClickListener: ((articleId: Int) -> Unit)? = null

    fun setOnItemClickListener(actionListener: ((articleId: Int) -> Unit)) {
        onItemClickListener = actionListener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemArticleListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: ArticleView) {
            binding.article = article
            binding.isShowAuthor = isShowAuthor
            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    getItem(adapterPosition).id?.let { it1 -> it(it1) }//todo
                }
            }
        }
    }

    object DiffCallBack : DiffUtil.ItemCallback<ArticleView>() {
        override fun areItemsTheSame(oldItem: ArticleView, newItem: ArticleView): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ArticleView, newItem: ArticleView): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
}