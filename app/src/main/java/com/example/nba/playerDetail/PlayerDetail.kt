package com.example.nba.playerDetail

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nba.data.Player

@Composable
fun PlayerDetail(player: Player, isFavorite: Boolean, onToggleFavorite: () -> Unit) {

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "${player.playerName} (${player.position})",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    ToggleFavoriteButton(isFavorite = isFavorite, onToggleFavorite = onToggleFavorite)
                }
            }

            item {
                Text(
                    text = "Age: ${player.age}, Team: ${player.team}",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            item {
                StatsSection(title = "Shooting Stats") {
                    StatRow(label = "Field Goals", value = "${player.fieldGoals}/${player.fieldAttempts}")
                    StatRow(label = "Field Goal %", value = "${player.fieldPercent}%")
                    StatRow(label = "3-Pt FGs", value = "${player.threeFg}/${player.threeAttempts}")
                    StatRow(label = "3-Pt %", value = player.threePercent?.let { "$it%" } ?: "N/A")
                    StatRow(label = "2-Pt FGs", value = "${player.twoFg}/${player.twoAttempts}")
                    StatRow(label = "2-Pt %", value = "${player.twoPercent}%")
                    StatRow(label = "Effective FG%", value = "${player.effectFgPercent}%")
                    StatRow(label = "Free Throws", value = "${player.ft}/${player.ftAttempts}")
                    StatRow(label = "FT %", value = player.ftPercent?.let { "$it%" } ?: "N/A")
                }
            }

            item {
                StatsSection(title = "Rebounding") {
                    StatRow(label = "Offensive Rebounds", value = player.offensiveRb.toString())
                    StatRow(label = "Defensive Rebounds", value = player.defensiveRb.toString())
                    StatRow(label = "Total Rebounds", value = player.totalRb.toString())
                }
            }

            item {
                StatsSection(title = "Other Stats") {
                    StatRow(label = "Assists", value = player.assists.toString())
                    StatRow(label = "Steals", value = player.steals.toString())
                    StatRow(label = "Blocks", value = player.blocks.toString())
                    StatRow(label = "Turnovers", value = player.turnovers.toString())
                    StatRow(label = "Personal Fouls", value = player.personalFouls.toString())
                    StatRow(label = "Points", value = player.points.toString())
                }
            }
        }
    }
}

@Composable
fun StatsSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                shape = RoundedCornerShape(8.dp)
            ),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            content()
        }
    }
}

@Composable
fun StatRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun ToggleFavoriteButton(isFavorite: Boolean, onToggleFavorite: () -> Unit) {
    Button(
        onClick = onToggleFavorite,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
            contentColor = if (isFavorite) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
        )
    ) {
        Icon(
            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = "Favorite",
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
    }

}

@Preview
@Composable
private fun PreviewPlayerDetail() {
    val player = Player(
        id = 1,
        playerName = "John Doe",
        position = "Guard",
        age = 25,
        team = "Team A",
        season = "2024",
        games = 10,
        gamesStarted = 10,
        minutesPg = 30.0f,
        fieldGoals = 100,
        fieldAttempts = 200,
        fieldPercent = 50.0f,
        threeFg = 50,
        threeAttempts = 100,
        threePercent = 50.0f,
        twoFg = 50,
        twoAttempts = 100,
        twoPercent = 50.0f,
        effectFgPercent = 50.0f,
        ft = 50,
        ftAttempts = 100,
        ftPercent = 50.0f,
        offensiveRb = 10,
        defensiveRb = 10,
        totalRb = 20,
        assists = 10,
        steals = 5,
        blocks = 5,
        turnovers = 5,
        personalFouls = 5,
        points = 30
    )
    PlayerDetail(player = player, isFavorite = false, onToggleFavorite = { })

}