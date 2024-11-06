package io.github.stream29.githubinsight.spider

class FetchException(
    message: String,
    cause: Exception
): Exception(message, cause)