package com.project.coinsight.presentation.ui.charts

import android.graphics.Color
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.MPPointF
import com.project.coinsight.domain.model.ChartData
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun CoinLineChart(
    chartData: List<ChartData>,
    modifier: Modifier = Modifier
) {
    AndroidView(
        factory = { context ->
            LineChart(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    500
                )
                description.isEnabled = false
                setTouchEnabled(true)
                setPinchZoom(true)
                isDoubleTapToZoomEnabled = true
                axisRight.isEnabled = false
                legend.isEnabled = false

                xAxis.apply {
                    position = XAxis.XAxisPosition.BOTTOM
                    granularity = 1f
                    setDrawGridLines(false)
                }

                axisLeft.apply {
                    textColor = Color.BLACK
                    textSize = 12f
                    setDrawGridLines(true)
                }

                val marker = ChartMarkerView(context)
                marker.setChartView(this) // This is the correct method
                marker.offset = MPPointF(-marker.width / 2f, -marker.height.toFloat())
                markerView = marker


            }
        },
        update = { chart ->
            val entries = chartData.mapIndexed { index, data ->
                Entry(index.toFloat(), data.price.toFloat())
            }

            val dateFormatter = SimpleDateFormat("MMM d", Locale.getDefault())
            val xAxisLabels = chartData.map { data ->
                dateFormatter.format(Date(data.timestamp))
            }

            val dataSet = LineDataSet(entries, "Price").apply {
                color = Color.BLUE
                valueTextColor = Color.BLACK
                setDrawCircles(false)
                setDrawValues(false)
                lineWidth = 2f
            }

            chart.xAxis.valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    val index = value.toInt().coerceIn(xAxisLabels.indices)
                    return xAxisLabels[index]
                }
            }

            chart.data = LineData(dataSet)
            chart.animateX(1000)
            chart.invalidate()
        },
        modifier = modifier
    )
}
