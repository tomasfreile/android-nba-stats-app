package com.example.nba.players.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.nba.R
import com.example.nba.ui.dimensions.Dimensions
import com.example.nba.utils.getTeamAbbreviationList

@Composable
fun DrawerContent(
    selectedTeams: Set<String>,
    onTeamToggle: (String) -> Unit,
) {

    val context = LocalContext.current
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(Dimensions.paddingLarge)) {
        Text(text = stringResource(R.string.filter_by_team), fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(Dimensions.paddingSmall))

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            val teams = getTeamAbbreviationList(context)
            items(teams) { team ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onTeamToggle(team) }
                        .padding(vertical = Dimensions.paddingXSmall)
                ) {
                    Checkbox(
                        checked = selectedTeams.contains(team),
                        onCheckedChange = { onTeamToggle(team) },
                        colors = CheckboxDefaults.colors(
                            checkmarkColor = MaterialTheme.colorScheme.primary,
                            checkedColor = MaterialTheme.colorScheme.onBackground,

                            )
                    )
                    Spacer(modifier = Modifier.width(Dimensions.paddingSmall))
                    Text(text = team)
                }
            }
        }
    }
}
