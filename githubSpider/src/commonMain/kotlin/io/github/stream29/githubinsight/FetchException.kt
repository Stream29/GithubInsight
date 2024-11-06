package io.github.stream29.githubinsight

class FetchException(
    message: String,
    cause: Exception
): Exception(message, cause)