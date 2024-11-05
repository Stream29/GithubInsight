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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SelectCountry() {
    var expanded by remember { mutableStateOf(false) }
    var selectedCountry by remember { mutableStateOf("None") }
    val countries = listOf("None","China","America","UK","Japan","Korea","Germany","France","Italy","Canada","Australia")
    Button(
        modifier = Modifier.width(120.dp).height(56.dp).padding(4.dp),
        onClick = { expanded = true }
    ) {
        Text(text = selectedCountry)
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier.width(120.dp).height(240.dp)
    ) {
        countries.forEach { country ->
            DropdownMenuItem(onClick = {
                selectedCountry = country
                expanded = false
            }) {
                Text(text = country)
            }
        }
    }
}
