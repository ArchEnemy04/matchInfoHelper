package com.alacrity.matchInfoHelper.ui.main.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alacrity.matchInfoHelper.entity.Example

@Composable
fun ScreenWithItems(list: List<Example>) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(list) { item ->
                ExampleView(example = item)
            }
        }
    }
}

@Composable
fun ExampleView(example: Example) {
    Card(modifier = Modifier, elevation = 10.dp) {
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(text = example.name ?: "blank name", modifier = Modifier.padding(5.dp))
            Text(text = example.email ?: "blank email", modifier = Modifier.padding(5.dp))
            Text(text = example.status ?: "blank status", modifier = Modifier.padding(5.dp))
            Text(text = example.gender ?: "blank gender", modifier = Modifier.padding(5.dp))
        }
    }

}