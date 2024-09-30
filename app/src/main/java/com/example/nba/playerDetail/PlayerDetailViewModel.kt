package com.example.nba.playerDetail

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nba.data.AppDatabase
import com.example.nba.data.FavoritePlayer
import com.example.nba.data.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerDetailViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _selectedPlayer = MutableStateFlow<Player?>(null)
    val selectedPlayer: StateFlow<Player?> = _selectedPlayer.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    private val appDatabase = AppDatabase.getInstance(context)

    fun getPlayerById(id: Int) {
        viewModelScope.launch {
            val player = appDatabase.playerDao().getPlayerById(id)
            _selectedPlayer.value = player

            val favorites = appDatabase.favoritePlayerDao().getAllFavorites()
            if (player != null) {
                _isFavorite.value = favorites.any { it.playerId == player.id }
            }
        }
    }

    fun toggleFavorite(playerId: Int) {
        viewModelScope.launch {
            val favorites = appDatabase.favoritePlayerDao().getAllFavorites()
            val isFavorite = favorites.any { it.playerId == playerId }
            if (isFavorite) {
                appDatabase.favoritePlayerDao().delete(FavoritePlayer(playerId))
                _isFavorite.value = false
                Toast.makeText(context, "Player removed from favorites", Toast.LENGTH_SHORT).show()
            } else {
                appDatabase.favoritePlayerDao().insert(FavoritePlayer(playerId))
                _isFavorite.value = true
                Toast.makeText(context, "Player added to favorites", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
