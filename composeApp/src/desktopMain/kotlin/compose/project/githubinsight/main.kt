package compose.project.githubinsight

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.github.githubinsight.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "ComposeDemo",
    ) {
        App()
    }
}