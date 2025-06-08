package com.project.coinsight.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(vertical = 4.dp)
    )
}


@Composable
fun InfoRow(label: String, value: String?) {
    if (!value.isNullOrBlank()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, style = MaterialTheme.typography.bodySmall)
            Text(text = value, style = MaterialTheme.typography.bodySmall)
        }
    }
}


@Composable
fun MarketInfoItem(label: String, value: Double?) {
    value?.let {
        InfoRow(label, "$${"%,.2f".format(it)}")
    }
}


