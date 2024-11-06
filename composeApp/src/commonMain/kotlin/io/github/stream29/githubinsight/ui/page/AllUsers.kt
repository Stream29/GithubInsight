package io.github.stream29.githubinsight.ui.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.stream29.githubinsight.entityMapExample
import io.github.stream29.githubinsight.ui.card.UserInfoCard
import io.github.stream29.githubinsight.ui.component.SearchBar
import io.github.stream29.githubinsight.ui.component.SelectCountry

@Composable
fun AllUsers(onStateChange: () -> Unit, globalUserLogin: MutableState<String>) {
    Column(horizontalAlignment = Alignment.Start) {
        Text(text = globalUserLogin.value)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(verticalArrangement = Arrangement.Center) {
                SelectCountry()
            }
            SearchBar(modifier = Modifier.align(Alignment.CenterVertically))
        }
        LazyColumn {
            var index = 1;
            for (login in entityMapExample.keys) {
                item {
                    UserInfoCard(index = index++.toString(),
                        entityMapExample[login]!!.userInfo,
                        globalUserLogin,
                        onStateChange)
                }
            }
        }
    }
}