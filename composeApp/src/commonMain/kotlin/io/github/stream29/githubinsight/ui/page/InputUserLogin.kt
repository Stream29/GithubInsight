package io.github.stream29.githubinsight.ui.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.stream29.githubinsight.ui.component.Form

@Composable
fun InputUserLogin(
    onStateChange: () -> Unit,
    globalUserLogin: MutableState<String>
) {
    val userMap = remember { mutableStateMapOf("user-login" to "") }
    Row(
        modifier = Modifier.fillMaxHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Form(
                form = userMap,
                type = "user-login",
                onStateChange = onStateChange,
                globalValue = globalUserLogin
            )
        }
    }
}
