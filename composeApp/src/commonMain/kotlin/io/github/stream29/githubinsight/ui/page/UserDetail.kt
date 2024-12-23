package io.github.stream29.githubinsight.ui.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import io.github.koalaplot.sample.polar.UserRadarChart
import io.github.stream29.githubinsight.UserEntities
import io.github.stream29.githubinsight.user

@Composable
fun UserDetail(
    onStateChange: () -> Unit,
    userLogin: MutableState<String>
) {
    user = UserEntities(userLogin.value)
    val user = user!!
    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.primary,
                modifier = Modifier.padding(10.dp),
                navigationIcon = {
                    IconButton(
                        onClick = { onStateChange() },
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier.padding(8.dp),
                            tint = MaterialTheme.colors.primary,
                        )
                    }
                },
                title = {
                    Text(
                        text = "User Information",
                        style = MaterialTheme.typography.h6
                    )
                }
            )
        }
    ) {
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Row {
                Column(modifier = Modifier.weight(1f)) {
                    AsyncImage(
                        model = user.userInfo.avatarUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(120.dp).clip(CircleShape)
                    )
                    Text(
                        text = "Name: ${user.userInfo.name}",
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.padding(8.dp)
                    )
                    Text(
                        text = "Bio: ${user.userInfo.bio}",
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.padding(8.dp)
                    )
                    if (user.userInfo.location != null) {
                        Text(
                            text = "Location: ${user.userInfo.location}",
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.padding(top = 8.dp, bottom = 30.dp)
                        )
                    } else {
                        Text(
                            text = "Inferred Location: ${user.userResult.nation.value}",
                            style = MaterialTheme.typography.h5,
                            color = Color(201, 79, 79),
                            modifier = Modifier.padding(8.dp)
                        )
                        Text(
                            text = "Confidence: ${user.userResult.nation.belief}",
                            style = MaterialTheme.typography.h5,
                            color = Color(201, 79, 79),
                            modifier = Modifier.padding(top = 8.dp, bottom = 30.dp)
                        )
                    }
                }
                Row {
                    UserRadarChart(
                        false,
                        user.userResult.talentRank,
                        Modifier.size(460.dp).align(Alignment.CenterVertically)
                    )
                }
            }



            Divider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 5.dp
            )

            LazyColumn {
                item {
                    user.userResult.talentRank.contributionMap.forEach {
                        Text(
                            text = "${it.key} ${it.value.second}: ${it.value.first}",
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}