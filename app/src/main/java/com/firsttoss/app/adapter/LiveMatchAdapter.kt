package com.firsttoss.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firsttoss.app.R
import com.firsttoss.app.data.Match

class LiveMatchAdapter(private val matches: List<Match>) :
    RecyclerView.Adapter<LiveMatchAdapter.MatchViewHolder>() {

    class MatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val type: TextView = itemView.findViewById(R.id.tv_match_type)
        val team1Name: TextView = itemView.findViewById(R.id.tv_team1_name)
        val team1Score: TextView = itemView.findViewById(R.id.tv_team1_score)
        val team1Overs: TextView = itemView.findViewById(R.id.tv_team1_overs)
        val team2Name: TextView = itemView.findViewById(R.id.tv_team2_name)
        val team2Score: TextView = itemView.findViewById(R.id.tv_team2_score)
        val team2Overs: TextView = itemView.findViewById(R.id.tv_team2_overs)
        val status: TextView = itemView.findViewById(R.id.tv_match_status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_live_match, parent, false)
        return MatchViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        val match = matches[position]
        holder.type.text = match.matchType
        holder.team1Name.text = match.team1.shortName
        holder.team2Name.text = match.team2.shortName

        // Rudimentary logic to display score if available
        if (!match.score.isNullOrEmpty()) {
            // Assuming first score is team 1, second is team 2 for simplicity in this mock
            // In a real API, we'd check inning or team IDs
            if (match.score.isNotEmpty()) {
                val s1 = match.score[0]
                holder.team1Score.text = "${s1.r}/${s1.w}"
                holder.team1Overs.text = "(${s1.o} ov)"
            }
             if (match.score.size > 1) {
                val s2 = match.score[1]
                holder.team2Score.text = "${s2.r}/${s2.w}"
                holder.team2Overs.text = "(${s2.o} ov)"
            } else {
                 holder.team2Score.text = ""
                 holder.team2Overs.text = ""
             }
        } else {
             holder.team1Score.text = "-"
             holder.team1Overs.text = ""
             holder.team2Score.text = "-"
             holder.team2Overs.text = ""
        }

        holder.status.text = match.status
    }

    override fun getItemCount() = matches.size
}
