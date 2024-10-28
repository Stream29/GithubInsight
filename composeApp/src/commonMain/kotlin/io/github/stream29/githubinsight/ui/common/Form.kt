package io.github.githubinsight.ui.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.dp

@Composable
fun Form(form: MutableMap<String, String>) =
    form.forEach { (key, value) ->
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = { form[key] = it },
                label = { Text(key) },
                modifier = Modifier.myModifier(200, 80)
            )
        }
    }

fun Modifier.myModifier(w: Int, h: Int) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "myModifier"
        properties["w"] = w
        properties["h"] = h
    },
    factory = {
        Modifier.width(w.dp).height(h.dp).padding(10.dp)
    }
)