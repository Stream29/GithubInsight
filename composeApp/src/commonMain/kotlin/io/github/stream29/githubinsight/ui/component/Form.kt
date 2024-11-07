package io.github.stream29.githubinsight.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun Form(
    form: MutableMap<String, String>,
    type: String,
    keyList: Array<String>? = null,
    onStateChange: () -> Unit,
    globalValue: MutableState<String>
) {
    val brush = remember {
        Brush.linearGradient(
            colors = listOf(Color(0xFFaa55ff), Color(0xFF55aaff), Color(0xFF9ce5ff))
        )
    }
    var isMenuOpen by remember { mutableStateOf(false) }
    var buttonText by remember { mutableStateOf("Select Model") }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Github Insight",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            style = TextStyle(brush),
            modifier = Modifier.padding(0.dp, 30.dp)
        )

        form.forEach { (key, value) ->
            OutlinedTextField(
                value = value,
                enabled = form.isNotEmpty(),
                shape = MaterialTheme.shapes.large,
                onValueChange = { form[key] = it },
                label = { Text(type) },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Edit"
                    )
                },
                placeholder = { Text("Please Enter $type") },
                textStyle = TextStyle(fontSize = 16.sp),
            )
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(top = 20.dp)
        ) {
            keyList?.let {
                Button(
                    onClick = { isMenuOpen = true },
                    content = {
                        Text(
                            text = buttonText, fontSize = 24.sp, fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(0.dp, 5.dp)
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color(68, 68, 68),
                        backgroundColor = Color.Transparent
                    ),
                    border = BorderStroke(3.dp, brush)
                )
            }
            Button(
                onClick = {
                    globalValue.value = form[type]!!
                    onStateChange()
                },
                content = {
                    Text(
                        text = "Submit", fontSize = 24.sp, fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(0.dp, 5.dp)
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color(68, 68, 68),
                    backgroundColor = Color.Transparent
                ),
                border = BorderStroke(3.dp, brush)
            )
        }

        DropdownMenu(
            expanded = isMenuOpen,
            onDismissRequest = { isMenuOpen = false },
            offset = DpOffset(30.dp, 380.dp),
            content = {
                keyList?.forEach { key ->
                    DropdownMenuItem(
                        onClick = {
                            form.clear()
                            form[key] = ""
                            isMenuOpen = !isMenuOpen
                            buttonText = "model:$key"
                        }
                    ) {
                        Text(
                            text = key,
                            fontSize = 24.sp
                        )
                    }
                }
            }
        )

        Text(
            text = "Debug:\n" + form.toMap().toString(),
            modifier = Modifier.padding(0.dp, 20.dp).offset(0.dp, 60.dp),
        )
    }
}
