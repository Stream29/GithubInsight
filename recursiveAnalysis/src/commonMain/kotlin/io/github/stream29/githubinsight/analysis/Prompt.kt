package io.github.stream29.githubinsight.analysis

fun functionCallPrompt(examples: List<GptFunctionExample>) =
    buildString {
        appendLine(
            """
            你可以通过调用函数来解决问题。通过函数调用得到的信息是可信的。
            当你需要调用函数时，请遵守以下语法：
            ===函数名=参数1=参数2====
            每次函数调用后，你将会在回复中得到函数调用的返回值，为一段文本。
            
            你只被允许调用以下的函数：
        """.trimIndent()
        )
        examples.forEachIndexed { index, example ->
            appendLine("########以下是关于第${index + 1}个函数的信息########")
            appendLine("函数名：${example.function.name}")
            if(example.parameter.isNotEmpty())
                appendLine("参数：${example.parameter.joinToString(", ", "[", "]")}")
            else
                appendLine("参数：无")
            appendLine(example.function.description)
            appendLine("使用示例：")
            appendLine("===${example.function.name}=${example.parameter.joinToString("=")}===")
            appendLine("返回值示例：")
            appendLine(example.result)
            appendLine("########以上是关于第${index + 1}个函数的信息########")
            appendLine()
            appendLine("如果你调用此外的函数，会被警告“非法调用”")
        }
    }