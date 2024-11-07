package io.github.koalaplot.sample.polar

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import io.github.koalaplot.core.ChartLayout
import io.github.koalaplot.core.Symbol
import io.github.koalaplot.core.legend.LegendLocation
import io.github.koalaplot.core.polar.PolarGraph
import io.github.koalaplot.core.polar.PolarGraphDefaults
import io.github.koalaplot.core.polar.PolarPlotSeries
import io.github.koalaplot.core.polar.PolarPoint
import io.github.koalaplot.core.polar.rememberCategoryAngularAxisModel
import io.github.koalaplot.core.polar.rememberFloatRadialAxisModel
import io.github.koalaplot.core.style.KoalaPlotTheme.axis
import io.github.koalaplot.core.style.LineStyle
import io.github.koalaplot.core.util.ExperimentalKoalaPlotApi
import io.github.koalaplot.core.util.toString
import io.github.stream29.githubinsight.common.entities.ContributionVector
import io.github.stream29.githubinsight.common.entities.UserResult
import io.github.stream29.githubinsight.ui.component.sample.ChartTitle
import io.github.stream29.githubinsight.ui.component.sample.paddingMod

@OptIn(ExperimentalKoalaPlotApi::class)
@Composable
@Suppress("MagicNumber")
fun UserRadarChart(thumbnail: Boolean, title: String, talentRank: ContributionVector) {
    val map = talentRank.contributionMap.toList().sortedBy { it.second.second }.reversed().toMap()
    val keys = map.keys.toList().take(10)
    val values = map.values.map { it.second.toFloat() }.toList().take(10)
    ChartLayout(
        modifier = paddingMod,
        title = { ChartTitle(title) },
        legendLocation = LegendLocation.BOTTOM
    ) {
        val ram = rememberFloatRadialAxisModel(listOf(0f, 20f, 40f, 60f, 80f, 100f)) // population in millions
        val aam = rememberCategoryAngularAxisModel(keys)

        val angularAxisGridLineStyle = if (thumbnail) {
            LineStyle(SolidColor(Color.LightGray), strokeWidth = 1.dp)
        } else {
            axis.majorGridlineStyle
        }

        PolarGraph(
            ram,
            aam,
            radialAxisLabels = { if (!thumbnail) Text(it.toString(1)) },
            { if (!thumbnail) Text(it.toString()) },
            polarGraphProperties = PolarGraphDefaults.PolarGraphPropertyDefaults()
                .copy(
                    angularAxisGridLineStyle = angularAxisGridLineStyle,
                    radialAxisGridLineStyle = angularAxisGridLineStyle
                )
        ) {
            val polarList: MutableList<PolarPoint<Float, String>> = mutableListOf()
            for(index in keys.indices){
                val point = PolarPoint<Float,String>(values[index], keys[index])
                polarList.add(point)
            }
            PolarPlotSeries(
                data = polarList,
                lineStyle = LineStyle(SolidColor(Color.Red), strokeWidth = 1.dp),
                symbols = {
                    Symbol(
                        shape = CircleShape,
                        fillBrush = SolidColor(Color.Red)
                    )
                }
            )
        }
    }
}