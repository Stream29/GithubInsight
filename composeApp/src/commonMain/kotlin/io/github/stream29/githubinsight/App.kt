package io.github.stream29.githubinsight

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.stream29.githubinsight.ui.card.InputKeyCard
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val userMap = remember { mutableStateMapOf("user-homepage-address" to "") }
    val keyMap = remember { mutableStateMapOf<String, String>() }
    val keyList = arrayOf("baidu", "gemini")
//    var selectedItem by remember { mutableStateOf(0) }
    MaterialTheme {
        Row(
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//                InputPathCard(userMap)
                InputKeyCard(keyMap, keyList)
            }
        }
//        Row(
//            modifier = Modifier.fillMaxHeight(),
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.Bottom
//        ) {
//            BottomNavigation(
//                windowInsets = BottomNavigationDefaults.windowInsets
//            ) {
//                BottomNavigationItem(
//                    icon = { Icon(Icons.Filled.Edit, contentDescription = null) },
//                    label = { Text("Test") },
//                    selected = selectedItem == 0,
//                    onClick = { selectedItem = 0 }
//                )
//            }
//        }
    }
}