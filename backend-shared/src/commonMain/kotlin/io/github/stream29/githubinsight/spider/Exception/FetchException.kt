package io.github.stream29.githubinsight.spider.Exception

class FetchException(
    message: String,
    cause: Exception
): Exception(message, cause)