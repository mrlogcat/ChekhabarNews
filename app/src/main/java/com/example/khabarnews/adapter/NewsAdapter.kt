package com.example.khabarnews.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.khabarnews.models.Article
import com.example.khabarnews.R

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {


    val differCallBack=object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
           return oldItem.url==newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem==newItem
        }

    }


    val differ=AsyncListDiffer(this,differCallBack)


    inner class  ArticleViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        var ivArticle: ImageView =itemView.findViewById(R.id.ivArticleImage)
        var tvSource: TextView =itemView.findViewById(R.id.tvSource)
        var tvTitle: TextView =itemView.findViewById(R.id.tvTitle)
        var tvDesc: TextView =itemView.findViewById(R.id.tvDescription)
        var tvPublishedAt: TextView =itemView.findViewById(R.id.tvPublishedAt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {

        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_article_preview,parent,false)
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article=differ.currentList[position]
        holder.apply {
            Glide.with(holder.itemView).load(article.urlToImage).into(holder.ivArticle)
            tvSource.text = article.source?.name
            tvTitle.text=article.title
            tvDesc.text=article.description
            tvPublishedAt.text=article.publishedAt


            holder.itemView.setOnClickListener{
                onItemClickListener?.let {
                    it(article)
                }
            }
        }
    }

    private var onItemClickListener:((Article)->Unit)?=null

    fun setOnItemClickListener(listener:(Article)->Unit){
        onItemClickListener=listener
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }
}