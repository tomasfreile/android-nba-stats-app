package com.example.nba.playerDetail.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.nba.R
import com.example.nba.data.Player
import com.example.nba.ui.dimensions.Dimensions

@Composable
fun PlayerDetail(player: Player, isFavorite: Boolean, onToggleFavorite: () -> Unit) {

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(Dimensions.paddingLarge),
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
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.weight(1f)
                    )
                    ToggleFavoriteButton(isFavorite = isFavorite, onToggleFavorite = onToggleFavorite)
                }
            }

            item {
                Text(
                    text = stringResource(id = R.string.age_label) + ": ${player.age}, " +
                            stringResource(id = R.string.team_label) + ": ${player.team}",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            item {
                StatsSection(title = stringResource(id = R.string.shooting_stats_title)) {
                    StatRow(label = stringResource(id = R.string.field_goals_label), value = "${player.fieldGoals}/${player.fieldAttempts}")
                    StatRow(label = stringResource(id = R.string.field_goal_percentage_label), value = "${player.fieldPercent}%")
                    StatRow(label = stringResource(id = R.string.three_pt_label), value = "${player.threeFg}/${player.threeAttempts}")
                    StatRow(label = stringResource(id = R.string.three_pt_percentage_label), value = player.threePercent?.let { "$it%" } ?: "N/A")
                    StatRow(label = stringResource(id = R.string.two_pt_label), value = "${player.twoFg}/${player.twoAttempts}")
                    StatRow(label = stringResource(id = R.string.two_pt_percentage_label), value = "${player.twoPercent}%")
                    StatRow(label = stringResource(id = R.string.effective_fg_percentage_label), value = "${player.effectFgPercent}%")
                    StatRow(label = stringResource(id = R.string.free_throws_label), value = "${player.ft}/${player.ftAttempts}")
                    StatRow(label = stringResource(id = R.string.free_throw_percentage_label), value = player.ftPercent?.let { "$it%" } ?: "N/A")
                }
            }

            item {
                StatsSection(title = stringResource(id = R.string.rebounding_title)) {
                    StatRow(label = stringResource(id = R.string.offensive_rebounds_label), value = player.offensiveRb.toString())
                    StatRow(label = stringResource(id = R.string.defensive_rebounds_label), value = player.defensiveRb.toString())
                    StatRow(label = stringResource(id = R.string.total_rebounds_label), value = player.totalRb.toString())
                }
            }

            item {
                StatsSection(title = stringResource(id = R.string.other_stats_title)) {
                    StatRow(label = stringResource(id = R.string.assists_label), value = player.assists.toString())
                    StatRow(label = stringResource(id = R.string.steals_label), value = player.steals.toString())
                    StatRow(label = stringResource(id = R.string.blocks_label), value = player.blocks.toString())
                    StatRow(label = stringResource(id = R.string.turnovers_label), value = player.turnovers.toString())
                    StatRow(label = stringResource(id = R.string.personal_fouls_label), value = player.personalFouls.toString())
                    StatRow(label = stringResource(id = R.string.points_label), value = player.points.toString())
                }
            }
        }
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