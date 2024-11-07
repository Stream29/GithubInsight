package io.github.stream29.githubinsight.ui.component.sample

import androidx.compose.runtime.Composable

interface SampleView {
    val name: String
    val thumbnail: @Composable () -> Unit
    val content: @Composable () -> Unit
}