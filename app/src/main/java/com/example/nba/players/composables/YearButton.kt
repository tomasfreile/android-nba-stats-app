package com.example.nba.players.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nba.ui.dimensions.Dimensions
import com.example.nba.ui.theme.Black

@Composable
fun YearButton(year: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() }
            .border(
                Dimensions.buttonBorder,
                Black,
                shape = RoundedCornerShape(Dimensions.yearButtonBorderRadius)
            ),
        shape = RoundedCornerShape(Dimensions.yearButtonBorderRadius),
        color = if (isSelected) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.primary
    ) {
        Text(
            text = year,
            color = if (isSelected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(horizontal = Dimensions.paddingLarge, vertical = Dimensions.paddingSmall)
        )
    }
}

@Preview
@Composable
fun PreviewYearButton() {
    YearButton(year = "2021", isSelected = false, onClick = {})
}

