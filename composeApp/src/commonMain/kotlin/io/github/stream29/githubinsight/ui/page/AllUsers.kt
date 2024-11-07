package io.github.stream29.githubinsight.ui.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.stream29.githubinsight.ui.card.UserInfoCard
import io.github.stream29.githubinsight.ui.component.SearchBar
import io.github.stream29.githubinsight.ui.component.SelectCountry
import io.github.stream29.githubinsight.userList

@Composable
fun AllUsers(onStateChange: () -> Unit, globalUserLogin: MutableState<String>) {
    var userList = userList!!
    val selectedCountry = remember { mutableStateOf("None") }
    val searchTopic = remember { mutableStateOf("") }
    Column(horizontalAlignment = Alignment.Start) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(verticalArrangement = Arrangement.Center) {
                SelectCountry(selectedCountry)
            }
            SearchBar(
                modifier = Modifier.align(Alignment.CenterVertically),
                searchTopic
            )
        }
        LazyColumn {
            if (searchTopic.value.isNotEmpty()) {
                userList = userList.sortedBy {
                    it
                        .userResult
                        .talentRank
                        .contributionMap
                        .getOrElse(searchTopic.value) { Pair(0, 0) }
                        .second
                }.reversed()
            }

            var index = 1
            userList.forEach() {
                if (selectedCountry.value == "None"
                    || it.userInfo.location == selectedCountry.value
                    || it.userResult.nation.value == selectedCountry.value
                ) {
                    item {
                        UserInfoCard(
                            index = index.toString(),
                            user = it,
                            globalUserLogin = globalUserLogin,
                            onStateChange = onStateChange
                        )
                        index++
                    }
                }
            }
        }
    }
}