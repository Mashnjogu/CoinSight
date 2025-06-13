package com.project.coinsight.presentation.ui.charts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.ButtonDefaults
import androidx.wear.compose.material3.Text
import com.project.coinsight.domain.model.ChartData

enum class TimeRange(val label: String, val durationMillis: Long){
    ONE_DAY("1D", 1 * 24 * 60 * 60 * 1000L),
    SEVEN_DAYS("7D", 7 * 24 * 60 * 60 * 1000L),
    ONE_MONTH("1M", 30 * 24 * 60 * 60 * 1000L)
}
@Composable
fun ChartWithTimeRangeSelector(
    allChartData: List<ChartData>, // full data with timestamps
    modifier: Modifier = Modifier
) {
    var selectedRange by remember { mutableStateOf(TimeRange.ONE_DAY) }

    val currentTime = rememberUpdatedState(System.currentTimeMillis())
    val filteredData by remember(allChartData, selectedRange){
        derivedStateOf {
            val now = currentTime.value
            allChartData.filter { it.timestamp >= now - selectedRange.durationMillis  }
        }
    }

    Column(modifier = modifier, content = chartContent(selectedRange, filteredData))
}

@Composable
private fun chartContent(
    selectedRange: TimeRange,
    filteredData: List<ChartData>
): @Composable() (ColumnScope.() -> Unit) {
    return {
        // Time Range Buttons
        TimeRangeSelector(selectedRange)

        Spacer(modifier = Modifier.height(8.dp))

        // Chart
        CoinLineChart(chartData = filteredData)
    }
}

@Composable
private fun TimeRangeSelector(selectedRange1: TimeRange) {
    var selectedRange11 = selectedRange1
    Row(modifier = Modifier.padding(8.dp)) {
        TimeRange.entries.forEach { range ->
            Button(
                onClick = { selectedRange11 = range },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedRange11 == range) Color.Blue else Color.Gray,
                    contentColor = Color.White
                ),
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                Text(text = range.label)
            }
        }
    }
}