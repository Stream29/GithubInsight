import io.github.stream29.githubinsight.analysis.*
import io.github.stream29.langchain4kt.core.asChatModel
import kotlinx.coroutines.runBlocking
import java.io.File
import kotlin.test.Test

class GptDispatchingTest {
    @Test
    fun dispatchedTest() {
        val model = functionCallingModel()
        runBlocking {
            model.chat("通过“询问”函数，问10次“从1加到100的答案是多少”，并且把结果统计给我，对回答的数学水平进行评价")
        }
        println(model.context)

    }
}

val functions = listOf(
    GptFunction {
        name("询问")
        description("向其他人询问")
        addParam("问题", "要委托的问题内容")
        resolveWith { parameters ->
            val question = parameters[0]
            val model = functionCallingModel()
            return@resolveWith runBlocking { model.chat(question) }
        }
    }.exampleExplained("水的密度是多少？") { "大约1×10^3kg/m^3" }
)

fun functionCallingModel(): FunctionCallingModel = FunctionCallingModel(
    qianfanApiProvider.asChatModel {
        systemInstruction(functionCallPrompt(functions))
    },
    functions
)