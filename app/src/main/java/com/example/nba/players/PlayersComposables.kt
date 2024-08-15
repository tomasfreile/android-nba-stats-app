package com.example.nba.players

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nba.R
import com.example.nba.ui.theme.Purple80

@Composable
fun Players() {
    val years = listOf("2024", "2023", "2022", "2021")
    val selectedYear = remember { mutableStateOf(years[0]) }
    val playerList = remember { mutableStateOf(listOf<Player>()) }

    // Function to fetch players from API (mocked for this example)
    fun fetchPlayers(year: String) {
        // In a real app, you would replace this with an API call
        playerList.value = listOf(
            Player("LeBron James", "Forward", 36, "LAL"),
            Player("Kevin Durant", "Forward", 32, "PHX"),
            Player("Stephen Curry", "Guard", 33, "GSW"),
            Player("Giannis Antetokounmpo", "Forward", 26, "MIL"),
            Player("Kawhi Leonard", "Forward", 30, "LAC"),
        )
    }

    Surface(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Column {
            // Dropdown Menu for Year Selection
            YearDropdownMenu(years = years, selectedYear = selectedYear.value) {
                selectedYear.value = it
                fetchPlayers(it)
            }

            // Player Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(8.dp)
            ) {
                items(playerList.value) { player ->
                    PlayerCard(player = player)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YearDropdownMenu(years: List<String>, selectedYear: String, onYearSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = selectedYear,
            onValueChange = {},
            label = { Text("Select Season") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            years.forEach { year ->
                DropdownMenuItem(
                    onClick = {
                        onYearSelected(year)
                        expanded = false
                    },
                    text = { Text(text = year) }
                )
            }
        }
    }
}

@Composable
fun PlayerCard(player: Player) {
    Surface(
        modifier = Modifier
            .size(width = 150.dp, height = 115.dp),
        shape = RoundedCornerShape(8.dp),
        color = Purple80
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = player.name)
            Text(text = "Position: ${player.position}")
            Text(text = "Age: ${player.age}")
            Text(text = "Team: ${player.team}")
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.End
        ) {
            Icon(imageVector = Icons.Outlined.Star, contentDescription = "")
        }
    }
}

data class Player(
    val name: String,
    val position: String,
    val age: Int,
    val team: String,
)

@Preview(device = "id:pixel_8_pro")
@Composable
private fun PlayersPreview() {
    Players()
}
