package io.github.stream29.githubinsight.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SelectCountry(selectedCountry: MutableState<String>) {
    var expanded by remember { mutableStateOf(false) }
    val countries = listOf("None","中国","美国","英国","日本","韩国","德国","法国","意大利","加拿大","澳大利亚")
    Button(
        modifier = Modifier.width(80.dp).height(56.dp).padding(4.dp),
        onClick = { expanded = true }
    ) {
        Text(text = selectedCountry.value)
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier.width(120.dp).height(240.dp)
    ) {
        countries.forEach { country ->
            DropdownMenuItem(onClick = {
                selectedCountry.value = country
                expanded = false
            }) {
                Text(text = country)
            }
        }
    }
}
