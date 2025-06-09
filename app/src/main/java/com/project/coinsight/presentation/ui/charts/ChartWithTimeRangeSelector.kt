package com.project.coinsight.presentation.ui.charts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.ButtonDefaults
import androidx.wear.compose.material3.Text
import com.project.coinsight.domain.model.ChartData

@Composable
fun ChartWithTimeRangeSelector(
    allChartData: List<ChartData>, // full data with timestamps
    modifier: Modifier = Modifier
) {
    var selectedRange by remember { mutableStateOf("1D") }

    val timeRanges = listOf("1D", "7D", "1M")

    val filteredData = remember(selectedRange, allChartData) {
        val now = System.currentTimeMillis()
        val rangeInMillis = when (selectedRange) {
            "1D" -> 1 * 24 * 60 * 60 * 1000L
            "7D" -> 7 * 24 * 60 * 60 * 1000L
            "1M" -> 30 * 24 * 60 * 60 * 1000L
            else -> Long.MAX_VALUE
        }
        allChartData.filter { it.timestamp >= now - rangeInMillis }
    }

    Column(modifier = modifier) {
        // Time Range Buttons
        Row(modifier = Modifier.padding(8.dp)) {
            timeRanges.forEach { range ->
                Button(
                    onClick = { selectedRange = range },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedRange == range) Color.Blue else Color.Gray,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.padding(horizontal = 4.dp)
                ) {
                    Text(text = range)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Chart
        CoinLineChart(chartData = filteredData)
    }
}