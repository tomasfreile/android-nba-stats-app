package com.example.nba.players.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.nba.ui.dimensions.Dimensions


@Composable
fun SearchBar(searchText: String, onSearchTextChange: (String) -> Unit) {
    TextField(
        value = searchText,
        onValueChange = { onSearchTextChange(it) },
        placeholder = { Text(text = stringResource(id = com.example.nba.R.string.search_bar_placeholder)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimensions.paddingSmall),
        shape = RoundedCornerShape(Dimensions.searchBarBorderRadius),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.primary,
            unfocusedContainerColor = MaterialTheme.colorScheme.primary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.onSurface,
        ),
    )
}

@Preview
@Composable
fun PreviewSearchBar() {
    SearchBar(searchText = "", onSearchTextChange = {})
}