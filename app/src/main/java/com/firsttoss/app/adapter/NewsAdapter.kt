package com.firsttoss.app.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firsttoss.app.R
import com.firsttoss.app.data.NewsItem

class NewsAdapter(private val context: Context, private var newsList: List<NewsItem>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tv_news_title)
        val time: TextView = itemView.findViewById(R.id.tv_news_time)
        val image: ImageView = itemView.findViewById(R.id.iv_news_cover)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = newsList[position]
        
        // Clean title and description
        holder.title.text = news.title?.trim() ?: "No Title"
        holder.time.text = news.pubDate ?: "" // Date handling could be improved later (e.g. "X hours ago")

        // Load Image (if available in description or if we add a scraping logic later)
        // For now, we try to extract it from description or use a placeholder based on text availability
        val imageUrl = news.getCoverImage()
        if (!imageUrl.isNullOrEmpty()) {
             Glide.with(context)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_cricket_ball) // Placeholder
                .into(holder.image)
             holder.image.clearColorFilter() // Remove tint if real image loads
        } else {
             holder.image.setImageResource(R.drawable.ic_cricket_ball)
        }

        // On Click -> Open URL
        holder.itemView.setOnClickListener {
            news.link?.let { url ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount() = newsList.size

    fun updateData(newData: List<NewsItem>) {
        newsList = newData
        notifyDataSetChanged()
    }
}
