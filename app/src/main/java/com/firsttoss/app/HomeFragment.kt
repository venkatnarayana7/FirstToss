package com.firsttoss.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firsttoss.app.R
import com.firsttoss.app.adapter.LiveMatchAdapter
import com.firsttoss.app.adapter.NewsAdapter
import com.firsttoss.app.data.Match
import com.firsttoss.app.data.Score
import com.firsttoss.app.data.Team
import com.firsttoss.app.network.ApiClient
import com.firsttoss.app.network.CricketApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var liveMatchesRecyclerView: RecyclerView
    private lateinit var liveMatchAdapter: LiveMatchAdapter
    private val matches = mutableListOf<Match>()

    private lateinit var newsRecyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    private val newsList = mutableListOf<com.firsttoss.app.data.NewsItem>()
    
    // Auto-refresh handler
    private val handler = android.os.Handler(android.os.Looper.getMainLooper())
    private val refreshRunnable = object : Runnable {
        override fun run() {
            fetchNews()
            handler.postDelayed(this, 300000) // 5 minutes (5 * 60 * 1000)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        
        // Setup Live Matches Schema
        liveMatchesRecyclerView = view.findViewById(R.id.rv_live_matches)
        liveMatchesRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        liveMatchAdapter = LiveMatchAdapter(matches)
        liveMatchesRecyclerView.adapter = liveMatchAdapter

        // Setup News Schema
        newsRecyclerView = view.findViewById(R.id.rv_news_feed)
        newsRecyclerView.layoutManager = LinearLayoutManager(context)
        newsAdapter = NewsAdapter(requireContext(), newsList)
        newsRecyclerView.adapter = newsAdapter

        fetchLiveMatches()
        
        // Start News Refresh Loop
        handler.post(refreshRunnable)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(refreshRunnable) // Stop updates when fragment destroyed
    }

    private fun fetchNews() {
        ApiClient.newsApiService.getCricketNews().enqueue(object : Callback<com.firsttoss.app.data.RssFeed> {
            override fun onResponse(
                call: Call<com.firsttoss.app.data.RssFeed>,
                response: Response<com.firsttoss.app.data.RssFeed>
            ) {
                if (response.isSuccessful) {
                    val feed = response.body()
                    val items = feed?.channel?.items
                    if (items != null) {
                        newsList.clear()
                        // Limit to top 10 latest news items
                        newsList.addAll(items.take(10))
                        newsAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<com.firsttoss.app.data.RssFeed>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun fetchLiveMatches() {
        // Use Mock Data for immediate demonstration (Free APIs require keys/registration)
        // To use real API: ApiClient.apiService.getLiveMatches().enqueue(...)
        
        val mockMatches = listOf(
            Match(
                id = "1",
                name = "IND vs AUS",
                matchType = "T20 International",
                status = "IND need 15 runs in 8 balls",
                venue = "Melbourne Cricket Ground",
                date = "2024-01-30",
                team1 = Team("India", "IND", ""),
                team2 = Team("Australia", "AUS", ""),
                score = listOf(
                    Score(210, 3, 18.4, "IND"),
                    Score(224, 5, 20.0, "AUS")
                )
            ),
            Match(
                id = "2",
                name = "ENG vs SA",
                matchType = "ODI Series",
                status = "England won by 20 runs",
                venue = "Lord's",
                date = "2024-01-29",
                team1 = Team("England", "ENG", ""),
                team2 = Team("South Africa", "SA", ""),
                score = listOf(
                    Score(280, 8, 50.0, "ENG"),
                    Score(260, 10, 48.2, "SA")
                )
            ),
             Match(
                id = "3",
                name = "CSK vs MI",
                matchType = "IPL League Match",
                status = "Live - 1st Innings",
                venue = "Wankhede Stadium",
                date = "2024-04-12",
                team1 = Team("Mumbai Indians", "MI", ""),
                team2 = Team("Chennai Super Kings", "CSK", ""),
                score = listOf(
                    Score(45, 1, 5.2, "MI")
                )
            )
        )

        matches.clear()
        matches.addAll(mockMatches)
        liveMatchAdapter.notifyDataSetChanged()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Setup logics here if needed (e.g. bottom nav listeners)
    }
}
