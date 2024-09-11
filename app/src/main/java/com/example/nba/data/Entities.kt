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
    val season: String
)
