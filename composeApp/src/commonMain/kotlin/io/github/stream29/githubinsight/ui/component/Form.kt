package io.github.stream29.githubinsight.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Form(form: MutableMap<String, String>) =
    Column {
        Text(
            text = "Please Enter Your Info",
            fontSize = 30.sp,
            modifier = Modifier.padding(0.dp, 20.dp)
        )
        form.forEach { (key, value) ->
            OutlinedTextField(
                value = value,
                onValueChange = { form[key] = it },
                label = { Text(key) },
                modifier = Modifier.myModifier(200, 50).align(Alignment.CenterHorizontally)
            )
        }
        Text(
            text = "Debug:\n" + form.toMap().toString(),
            modifier = Modifier.padding(0.dp, 20.dp)
        )
    }

fun Modifier.myModifier(w: Int, h: Int) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "myModifier"
        properties["w"] = w
        properties["h"] = h
    },
    factory = {
        Modifier.width(w.dp).height(h.dp)
    }
)