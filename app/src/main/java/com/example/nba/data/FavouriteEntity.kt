package com.example.nba.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_players")
data class FavoritePlayer(
    @PrimaryKey val playerId: Int
)
