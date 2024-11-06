package io.github.stream29.githubinsight

import com.charleskorn.kaml.PolymorphismStyle
import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.YamlConfiguration

val yaml = Yaml(
    configuration = YamlConfiguration(
        polymorphismStyle = PolymorphismStyle.Property,
        polymorphismPropertyName = "type"
    )
)