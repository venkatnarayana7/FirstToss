package com.firsttoss.app.data

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "rss")
data class RssFeed(
    @Element val channel: Channel
)

@Xml(name = "channel")
data class Channel(
    @Element(name = "item") val items: List<NewsItem>?
)

@Xml(name = "item")
data class NewsItem(
    @PropertyElement(name = "title") val title: String?,
    @PropertyElement(name = "description") val description: String?,
    @PropertyElement(name = "link") val link: String?,
    @PropertyElement(name = "pubDate") val pubDate: String?
) {
    // Helper to strip HTML tags from description (e.g. <p>, <img>)
    fun getCleanDescription(): String {
        return description?.replace(Regex("<.*?>"), "")?.trim() ?: ""
    }

    // Helper to extract image URL if present in description (simple regex)
    fun getCoverImage(): String? {
        val regex = Regex("src\\s*=\\s*\"(.+?)\"")
        val match = description?.let { regex.find(it) }
        return match?.groupValues?.get(1)
    }
}
