package com.firsttoss.app.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    // Using a placeholder base URL. User can change this to a real API like cricketdata.org
    private const val BASE_URL = "https://api.cricapi.com/v1/" 
    
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: CricketApiService = retrofit.create(CricketApiService::class.java)

    // News API Client (XML)
    private const val NEWS_BASE_URL = "https://www.espncricinfo.com/"

    val newsRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(NEWS_BASE_URL)
        .addConverterFactory(
            com.tickaroo.tikxml.retrofit.TikXmlConverterFactory.create(
                com.tickaroo.tikxml.TikXml.Builder().exceptionOnUnreadXml(false).build()
            )
        )
        .build()

    val newsApiService: NewsApiService = newsRetrofit.create(NewsApiService::class.java)
}
