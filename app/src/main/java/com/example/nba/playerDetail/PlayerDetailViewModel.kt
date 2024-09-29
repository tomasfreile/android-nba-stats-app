package com.example.nba.playerDetail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nba.data.AppDatabase
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

    private val appDatabase = AppDatabase.getInstance(context)

    fun getPlayerById(id: Int) {
        viewModelScope.launch {
            val player = appDatabase.playerDao().getPlayerById(id)
            _selectedPlayer.value = player
        }
    }
}
