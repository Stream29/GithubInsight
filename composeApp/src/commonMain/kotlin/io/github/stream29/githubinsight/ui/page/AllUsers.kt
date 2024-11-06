package io.github.stream29.githubinsight.ui.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.stream29.githubinsight.type.UserInfo
import io.github.stream29.githubinsight.ui.card.UserInfoCard
import io.github.stream29.githubinsight.ui.component.SearchBar
import io.github.stream29.githubinsight.ui.component.SelectCountry

@Composable
fun AllUsers(onStateChange: () -> Unit) {
    Column(horizontalAlignment = Alignment.Start) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(verticalArrangement = Arrangement.Center) {
                SelectCountry()
            }
            SearchBar(modifier = Modifier.align(Alignment.CenterVertically))
        }
        LazyColumn {
            for (i in 1..30) {
                item {
                    UserInfoCard(index = i.toString(), userInfo = UserInfo)
                }
            }
        }
    }
}