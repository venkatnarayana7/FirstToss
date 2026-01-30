package com.firsttoss.app.network

import com.firsttoss.app.data.RssFeed
import retrofit2.Call
import retrofit2.http.GET

interface NewsApiService {
    // base URL will be https://www.espncricinfo.com/
    @GET("rss/content/story/feeds/0.xml")
    fun getCricketNews(): Call<RssFeed>
}
