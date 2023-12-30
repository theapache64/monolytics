package com.github.theapache64.monolytics.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CommaInputUi(
    actionTitle : String,
    modifier: Modifier = Modifier,
    onInputSubmitted: (List<String>) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // TextField to collect comma separated names
        var text by remember { mutableStateOf("") }

        TextField(
            value = text,
            onValueChange = { text = it },
            label = {
                Text("Enter comma separated names")
            }
        )

        Button(
            onClick = {
                onInputSubmitted(text.split(",").map { it.trim() })
            },
        ) {
            Text(text = actionTitle)
        }
    }
}