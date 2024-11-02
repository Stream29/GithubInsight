package io.github.stream29.githubinsight.analysis

import io.github.stream29.langchain4kt.core.ChatModel

data class FunctionCallingModel(
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

@Suppress("function_name")
fun FunctionCallingModel(baseModel: ChatModel, functionExplained: List<GptFunctionExample>): FunctionCallingModel {
    val functionMap = functionExplained.associate { it.function.name to it.function.resolve }
    return FunctionCallingModel(
        baseModel,
        resolveFunctionCall = ::resolveFunctionCall,
        onFunctionCall = {
            functionMap[it.functionName]?.invoke(it.args)
                ?: "警告：非法调用"
        },
    )
}

private fun resolveFunctionCall(message: String): FunctionCall? {
    if (!message.contains("===")) {
        return null
    }
    val parameterList = message.substringAfter("===")
        .splitToSequence("=")
        .filter { it != "" }
        .toList()
    return FunctionCall(
        parameterList[0],
        parameterList.drop(1)
    )
}

const val stopSequence = "===="