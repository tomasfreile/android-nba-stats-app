package com.example.nba.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlayerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayers(players: List<Player>)

    @Query("SELECT * FROM players WHERE id = :playerId")
    suspend fun getPlayerById(playerId: Int): Player?

    @Query("SELECT * FROM players WHERE season = :season")
    suspend fun getPlayersBySeason(season: String): List<Player>
}

@Dao
interface FavoritePlayerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(player: FavoritePlayer)

    @Delete
    suspend fun delete(player: FavoritePlayer)

    @Query("SELECT * FROM favorite_players")
    suspend fun getAllFavorites(): List<FavoritePlayer>
}

