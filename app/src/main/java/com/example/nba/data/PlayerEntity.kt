package com.example.nba.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "players")
data class Player(
    @PrimaryKey val id: Int,
    val playerName: String,
    val position: String,
    val age: Int,
    val team: String,
    val season: String,
    val games: Int,
    val gamesStarted: Int,
    val minutesPg: Float,
    val fieldGoals: Int,
    val fieldAttempts: Int,
    val fieldPercent: Float,
    val threeFg: Int,
    val threeAttempts: Int,
    val threePercent: Float?,
    val twoFg: Int,
    val twoAttempts: Int,
    val twoPercent: Float,
    val effectFgPercent: Float,
    val ft: Int,
    val ftAttempts: Int,
    val ftPercent: Float?,
    val offensiveRb: Int,
    val defensiveRb: Int,
    val totalRb: Int,
    val assists: Int,
    val steals: Int,
    val blocks: Int,
    val turnovers: Int,
    val personalFouls: Int,
    val points: Int,
)
