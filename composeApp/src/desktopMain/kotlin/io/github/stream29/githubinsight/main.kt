package io.github.stream29.githubinsight

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "ComposeDemo",
    ) {
        App()
    }
}