import io.github.stream29.githubinsight.analysis.*
import io.github.stream29.langchain4kt.core.asChatModel
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import kotlin.test.Test

class GptFunctionCallingTest {
    @Test
    fun simpleFunctionCall() {
        val model = FunctionCallingModel(
            qianfanApiProvider.asChatModel {
                systemInstruction(functionCallPrompt(functions))
            },
            functions
        )
        runBlocking {
            model.chat("现在几点？")
        }
        println(model.context)
    }
}

val functions = listOf(
    GptFunction {
        name("查询现在时间")
        description("返回现在的时间")
        resolveWith { _ -> LocalDateTime.now().toString() }
    }.exampleExplained(),
)

