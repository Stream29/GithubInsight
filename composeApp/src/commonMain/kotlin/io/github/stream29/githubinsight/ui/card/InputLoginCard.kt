package io.github.stream29.githubinsight.ui.card

import androidx.compose.runtime.Composable
import io.github.stream29.githubinsight.ui.component.Form

@Composable
fun InputLoginCard(
    keyMap: MutableMap<String, String>,
    onStateChange: () -> Unit
) {
    Form(
        form = keyMap,
        type = "userLogin",
        onStateChange = onStateChange
    )
}