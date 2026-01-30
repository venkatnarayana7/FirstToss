package com.firsttoss.app.network

import com.firsttoss.app.data.Match
import retrofit2.Call
import retrofit2.http.GET

interface CricketApiService {
    @GET("matches?apikey=YOUR_API_KEY") // Placeholder endpoint
    fun getLiveMatches(): Call<List<Match>>
}
