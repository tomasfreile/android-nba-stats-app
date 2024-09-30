package com.example.nba.playerDetail.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.nba.ui.dimensions.Dimensions

@Composable
fun StatsSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = Dimensions.statCardBorder,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                shape = RoundedCornerShape(Dimensions.statCardBorderRadius)
            ),
        shape = RoundedCornerShape(Dimensions.statCardBorderRadius),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier.padding(Dimensions.paddingLarge),
            verticalArrangement = Arrangement.spacedBy(Dimensions.paddingSmall)
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