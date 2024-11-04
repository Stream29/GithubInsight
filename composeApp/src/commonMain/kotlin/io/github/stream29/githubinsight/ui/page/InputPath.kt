package io.github.stream29.githubinsight.ui.page

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import io.github.stream29.githubinsight.ui.card.InputPathCard

@Composable
fun InputPath(onStateChange: () -> Unit) {
    val userMap = remember { mutableStateMapOf("user-homepage-address" to "") }
    InputPathCard(userMap, onStateChange)
}
