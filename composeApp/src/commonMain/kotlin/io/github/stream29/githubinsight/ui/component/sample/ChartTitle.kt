package io.github.stream29.githubinsight.ui.component.sample

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun ChartTitle(title: String) {
    Column {
        Text(
            title,
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}