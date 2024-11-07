package io.github.stream29.githubinsight

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.github.koalaplot.sample.polar.UserRadarChart
import io.github.stream29.githubinsight.ui.page.AllUsers
import io.github.stream29.githubinsight.ui.page.InputUserLogin
import io.github.stream29.githubinsight.ui.page.UserDetail

@Composable
fun App() {
    var pageState by remember { mutableStateOf(-100) } //调试
    val globalUserLogin = remember { mutableStateOf("") }
    MaterialTheme {
        when (pageState) {
            0 -> InputUserLogin { pageState = 1 }
            1 -> UserDetail({ pageState = 2 }, globalUserLogin)
            2 -> AllUsers({ pageState = 3 }, globalUserLogin)
            3 -> UserDetail({ pageState = 2 }, globalUserLogin)

            else -> UserRadarChart(false, "User Radar Chart", user!!.userResult.talentRank)
        }
    }
}