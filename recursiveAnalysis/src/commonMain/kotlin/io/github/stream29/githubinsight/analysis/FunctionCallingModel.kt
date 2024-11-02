package io.github.stream29.githubinsight.analysis

import io.github.stream29.langchain4kt.core.ChatModel

class FunctionCallingModel(
    val baseModel: ChatModel,
    val resolveFunctionCall: (String) -> FunctionCall?,
    val onFunctionCall: (FunctionCall) -> String
) : ChatModel by baseModel {
    override suspend fun chat(message: String): String {
        val response = baseModel.chat(message)
        return resolveFunctionCall(response)?.let{ onFunctionCall(it) }?.let{ baseModel.chat(it) } ?: response
    }
}

data class FunctionCall(
    val functionName: String,
    val args: List<String>
)