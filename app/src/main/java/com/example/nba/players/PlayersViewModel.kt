package com.example.nba.players

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nba.R
import com.example.nba.apiManager.ApiServiceImpl
import com.example.nba.data.AppDatabase
import com.example.nba.data.Player
import com.example.nba.data.PlayerDao
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayersViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiService: ApiServiceImpl,
) : ViewModel() {
    private val _players = MutableStateFlow(listOf<Player>())
    val players: StateFlow<List<Player>> = _players.asStateFlow()

    private val _selectedPlayer = MutableStateFlow<Player?>(null)
    val selectedPlayer: StateFlow<Player?> = _selectedPlayer

    private val _loadingPlayers = MutableStateFlow(false)
    val loadingPlayers: StateFlow<Boolean> = _loadingPlayers.asStateFlow()

    private val _showRetryButton = MutableStateFlow(false)
    val showRetryButton: StateFlow<Boolean> = _showRetryButton.asStateFlow()

    private val appDatabase = AppDatabase.getInstance(context)

    init {
        fetchPlayers("2024")
    }

    fun fetchPlayers(season: String){
        _loadingPlayers.value = true
        viewModelScope.launch {
            val playersFromDB = appDatabase.playerDao().getPlayersBySeason(season)
            if(playersFromDB.isNotEmpty()){
                _players.value = playersFromDB.sortedBy { player -> player.playerName }
                _loadingPlayers.value = false
                Log.d("PlayersViewModel", "Players fetched from DB")
            } else {
                Log.d("PlayersViewModel", "Fetching players from API")
                apiService.getPlayers(
                    season = season,
                    context = context,
                    onSuccess = {
                        viewModelScope.launch {
                            appDatabase.playerDao().insertPlayers(it)
                            _players.value = it.sortedBy { player -> player.playerName }
                            Log.d("PlayersViewModel", "Players fetched from API and saved to DB")
                        }
                        _showRetryButton.value = false
                    },
                    onFail = {
                        _showRetryButton.value = true
                    },
                    loadingFinished = {
                        _loadingPlayers.value = false
                    }
                )
            }
        }
    }
    fun retryLoadingPlayers(season: String) {
        fetchPlayers(season)
    }

    fun getPlayerById(id: Int) {
        viewModelScope.launch {
            val player = appDatabase.playerDao().getPlayerById(id)
            _selectedPlayer.value = player
        }
    }
}