package io.github.stream29.githubinsight

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.github.stream29.githubinsight.ui.page.AllUsers
import io.github.stream29.githubinsight.ui.page.InputPath

@Composable
fun App() {
    var pageState by remember { mutableStateOf(0) }
    MaterialTheme {
        when (pageState) {
            0 -> InputPath { pageState = 1 }
            1 -> AllUsers { pageState = 2 }
        }
    }
}