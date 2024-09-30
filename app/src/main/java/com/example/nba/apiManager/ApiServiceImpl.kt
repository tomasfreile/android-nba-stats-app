package com.example.nba.apiManager

import android.content.Context
import android.widget.Toast
import com.example.nba.R
import com.example.nba.data.Player

import retrofit.GsonConverterFactory
import retrofit.Retrofit
import retrofit.Call
import retrofit.Callback
import retrofit.Response
import javax.inject.Inject

class ApiServiceImpl @Inject constructor() {

    fun getPlayers(
        season: String,
        context: Context,
        onSuccess: (List<Player>) -> Unit,
        onFail: () -> Unit,
        loadingFinished: () -> Unit
    ) {

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(context.getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: ApiService = retrofit.create(ApiService::class.java)

        val call: Call<List<Player>> = service.getPlayersBySeason(season)

        call.enqueue(object : Callback<List<Player>> {
            override fun onResponse(response: Response<List<Player>>?, retrofit: Retrofit?) {
                loadingFinished()
                if (response?.isSuccess == true) {
                    val players: List<Player> = response.body()
                    onSuccess(players)
                }
                 else {
                    onFailure(Exception(context.getString(R.string.toast_error_getting_players)))
                }
            }
            override fun onFailure(t: Throwable?) {
                Toast.makeText(context, context.getString(R.string.toast_error_getting_players), Toast.LENGTH_SHORT).show()
                onFail()
                loadingFinished()
            }
        })
    }
}