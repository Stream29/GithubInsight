import io.github.stream29.githubinsight.analysis.FunctionCall
import io.github.stream29.githubinsight.analysis.FunctionCallingModel
import io.github.stream29.langchain4kt.core.asChatModel
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class FunctionCallingTest {
    @Test
    fun simpleFunctionCall() {
        val model = FunctionCallingModel(
            qianfanApiProvider.asChatModel {
                systemInstruction(systemInstruction)
            },
            resolveFunctionCall = ::resolveFunctionCall,
            onFunctionCall = ::onFunctionCall,
        )
        runBlocking {
            model.chat("现在几点？")
        }
        println(model.context)
    }
}

fun resolveFunctionCall(message: String): FunctionCall? {
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

fun onFunctionCall(call: FunctionCall): String =
    functionMap[call.functionName]?.invoke(call.args)
        ?: "警告：非法调用"


val functionMap = mapOf<String, (List<String>) -> String>(
    "查询现在时间" to { "2024-11-2 10:33" }
)

val systemInstruction = """
    你可以通过调用函数来解决问题。通过函数调用得到的信息是可信的。
    当你需要调用函数时，请遵守以下语法：
    ===函数名=参数1=参数2====
    例如：
    你需要调用函数“搜索”，参数为“太阳的年龄是多大？”
    你应当输出：
    ===搜索=太阳的年龄是多大====
    例如：
    你需要调用函数“打开微信”，没有参数
    你应当输出：
    ===打开微信====
    每次函数调用后，你将会在回复中得到函数调用的返回值，为一段文本。
    
    你只被允许调用以下的函数：
    
    查询现在时间，没有参数
    
    如果你调用此外的函数，会被警告“非法调用”
""".trimIndent()

val stopSequence = "===="