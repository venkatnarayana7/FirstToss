package com.firsttoss.app.data

data class Match(
    val id: String,
    val name: String,
    val matchType: String,
    val status: String,
    val venue: String,
    val date: String,
    val team1: Team,
    val team2: Team,
    val score: List<Score>? // List of scores (innings)
)

data class Team(
    val name: String,
    val shortName: String,
    val img: String // URL to team logo
)

data class Score(
    val r: Int, // Runs
    val w: Int, // Wickets
    val o: Double, // Overs
    val inning: String
)
