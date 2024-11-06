package io.github.stream29.githubinsight.ui.card

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import io.github.stream29.githubinsight.type.UserInfo

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UserInfoCard(
    index: String,
    userInfo: UserInfo = UserInfo,
    globalUserName: MutableState<String>,
    onStateChange: () -> Unit
) {
    Card(
        contentColor = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
        onClick = {
            globalUserName.value = userInfo.name
            onStateChange()
        }
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = index,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = userInfo.bio
            )
            AsyncImage(
                model = "https://avatars.githubusercontent.com/u/16459786?v=4",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(48.dp).clip(CircleShape)
            )
            Text(text = userInfo.name, modifier = Modifier.padding(start = 8.dp))
        }
    }
}
