package com.example.nba.players.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nba.ui.dimensions.Dimensions

@Composable
fun YearButtonList(
    listState: LazyListState,
    years: List<String>,
    selectedYear: MutableState<String>
) {
    LazyRow(
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(Dimensions.paddingSmall),
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimensions.paddingSmall)
    ) {
        items(years) { year ->
            YearButton(
                year = year,
                isSelected = selectedYear.value == year,
                onClick = { selectedYear.value = year }
            )
        }
    }
}