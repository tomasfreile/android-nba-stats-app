package com.example.nba.apiManager


import com.example.nba.data.Player
import retrofit.http.GET
import retrofit.http.Path
import retrofit.Call
import retrofit.http.Query

interface ApiService {
        @GET("PlayerDataTotals/season/{season}")
        fun getPlayersBySeason(
            @Path("season") season: String,
        ): Call<List<Player>>

}