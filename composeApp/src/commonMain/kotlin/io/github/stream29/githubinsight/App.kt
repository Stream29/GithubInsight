package io.github.stream29.githubinsight

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.githubinsight.ui.common.Form
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val formMap = remember { mutableStateMapOf("api key" to "", "secret key" to "") }
    MaterialTheme {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Form(formMap)
            Text(formMap.toMap().toString())
        }
    }
}