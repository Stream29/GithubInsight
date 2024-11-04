package io.github.stream29.githubinsight.ui.card

import androidx.compose.runtime.Composable
import io.github.stream29.githubinsight.ui.component.Form

@Composable
fun InputKeyCard(
    keyMap: MutableMap<String, String>,
    keyList: Array<String>,
    onStateChange: () -> Unit
) {
    Form(
        form = keyMap,
        type = "Key",
        keyList = keyList,
        onStateChange = onStateChange
    )
}