package io.github.stream29.githubinsight

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.stream29.githubinsight.ui.page.InputKey
import io.github.stream29.githubinsight.ui.page.InputPath

@Composable
fun App() {
    var pageState by remember { mutableStateOf(0) }
    MaterialTheme {
        Row(
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (pageState) {
                    0 -> InputKey { pageState = 1 }
                    1 -> InputPath { pageState = 2 }
                }
            }
        }
    }
}