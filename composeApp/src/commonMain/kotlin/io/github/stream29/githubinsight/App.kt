package io.github.stream29.githubinsight

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.github.stream29.githubinsight.ui.page.AllUsers
import io.github.stream29.githubinsight.ui.page.InputServerUrl
import io.github.stream29.githubinsight.ui.page.InputUserLogin
import io.github.stream29.githubinsight.ui.page.UserDetail

@Composable
fun App() {
    var pageState by remember { mutableStateOf(-1) }
    val globalUserLogin = remember { mutableStateOf("") }
    val globalBaseUtl = remember { mutableStateOf("") }
    MaterialTheme {
        when (pageState) {
            -1 -> InputServerUrl({ pageState = 0 }, globalBaseUtl)
            0 -> InputUserLogin({ pageState = 1 }, globalUserLogin)
            1 -> UserDetail({ pageState = 2 }, globalUserLogin)
            2 -> AllUsers({ pageState = 3 }, globalUserLogin)
            3 -> UserDetail({ pageState = 2 }, globalUserLogin)
        }
    }
}