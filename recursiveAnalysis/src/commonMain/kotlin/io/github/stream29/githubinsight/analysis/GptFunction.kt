package io.github.stream29.githubinsight.analysis

@GptFunctionDsl
fun GptFunctionBuilder.name(name: String) = apply { this.name = name }

@GptFunctionDsl
fun GptFunctionBuilder.addParameter(name: String, description: String) =
    apply { parameters.add(GptParameter(name, description)) }

@GptFunctionDsl
fun GptFunctionBuilder.description(description: String) = apply { this.description = description }

@GptFunctionDsl
fun GptFunctionBuilder.resolveWith(body: (List<String>) -> String) = apply { this.resolve = body }

@GptFunctionDsl
fun GptFunction(builder: GptFunctionBuilder.() -> Unit): GptFunction {
    val apply = GptFunctionBuilder().apply(builder)
    return GptFunction(
        name = requireNotNull(apply.name),
        parameters = apply.parameters,
        description = requireNotNull(apply.description),
        resolve = requireNotNull(apply.resolve)
    )
}

@GptFunctionDsl
fun GptFunction.exampleExplained(vararg parameters: String) =
    GptFunctionExample(this, parameters.toList(), resolve(parameters.toList()))

data class GptFunction(
    val name: String,
    val parameters: List<GptParameter>,
    val description: String,
    val resolve: (List<String>) -> String
)

data class GptFunctionExample(
    val function: GptFunction,
    val parameter: List<String>,
    val result: String
)

data class GptParameter(
    val name: String,
    val description: String
)

data class GptFunctionBuilder(
    internal var name: String? = null,
    internal var parameters: MutableList<GptParameter> = mutableListOf(),
    internal var description: String? = null,
    internal var resolve: ((List<String>) -> String)? = null
)
