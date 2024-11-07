package io.github.stream29.githubinsight

import io.github.stream29.githubinsight.common.BackendApiProvider
import io.github.stream29.githubinsight.common.RemoteBackendApiProvider

actual var backendApiProvider: BackendApiProvider =
    RemoteBackendApiProvider(httpClient, "http://192.168.1.199:8081")