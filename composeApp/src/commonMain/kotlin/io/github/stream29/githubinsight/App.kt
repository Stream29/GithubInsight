package io.github.stream29.githubinsight

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.github.stream29.githubinsight.ui.page.AllUsers
import io.github.stream29.githubinsight.ui.page.InputUserLogin
import io.github.stream29.githubinsight.ui.page.UserDetail

@Composable
fun App() {
    var pageState by remember { mutableStateOf(0) }
    val globalUserLogin = remember { mutableStateOf("") }
    MaterialTheme {
        when (pageState) {
            0 -> InputUserLogin { pageState = 1 }
            1 -> AllUsers (onStateChange = { pageState = 2 }, globalUserLogin )
            2 -> UserDetail({ pageState = 1 }, globalUserLogin.value)
        }
    }
}