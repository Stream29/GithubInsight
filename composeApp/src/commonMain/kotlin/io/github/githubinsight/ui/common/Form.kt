package io.github.githubinsight.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Form(form: MutableMap<String, String>) = Row {
    Column(
        modifier = Modifier.weight(1f, true),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        form.forEach { (key, value) ->
            Row(
                modifier = Modifier.weight(1f, false),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = key,
                    modifier = Modifier.padding(10.dp)
                )
                TextField(
                    value = value,
                    modifier = Modifier.height(50.dp).width(200.dp),
                    onValueChange = { form[key] = it }
                )
            }
        }
    }
}

