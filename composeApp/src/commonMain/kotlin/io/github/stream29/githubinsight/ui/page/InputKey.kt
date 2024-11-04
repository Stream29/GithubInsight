package io.github.stream29.githubinsight.ui.page

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import io.github.stream29.githubinsight.ui.card.InputKeyCard

@Composable
fun InputKey(onStateChange: () -> Unit) {
    val keyMap = remember { mutableStateMapOf<String, String>() }
    val keyList = arrayOf("baidu", "gemini")
    InputKeyCard(keyMap, keyList, onStateChange)
}