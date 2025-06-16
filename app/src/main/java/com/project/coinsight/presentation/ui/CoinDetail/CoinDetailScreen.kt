package com.project.coinsight.presentation.ui.CoinDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.project.coinsight.presentation.components.InfoRow
import com.project.coinsight.presentation.components.MarketInfoItem
import com.project.coinsight.presentation.components.SectionTitle
import com.project.coinsight.presentation.ui.charts.ChartWithTimeRangeSelector
import com.project.coinsight.presentation.ui.charts.CoinLineChart

@Composable
fun CoinDetailScreen(
    coinId: String,
){
    val coinDetailViewModel = hiltViewModel<CoinDetailViewModel>()
    val state = coinDetailViewModel.uiState.collectAsState().value

    LaunchedEffect(coinId) {
        coinDetailViewModel.loadCoinDetails(coinId)
    }

    Box(modifier = androidx.compose.ui.Modifier.fillMaxSize()) {
        state.coin?.let { coin ->
            LazyColumn(
                modifier = androidx.compose.ui.Modifier.fillMaxSize(),
                contentPadding = PaddingValues(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header Info
                item {
                    Text(
                        text = "#${coin.marketCapRank} ${coin.name} (${coin.symbol.uppercase()})",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }

                // Coin Image
                item {
                    androidx.compose.foundation.Image(
                        painter = rememberAsyncImagePainter(coin.imageUrl),
                        contentDescription = "${coin.name} Logo",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                    )
                }

                //chart
                item{
                    SectionTitle("Price Chart")
//                    CoinLineChart(chartData = state.chartData)
                    ChartWithTimeRangeSelector(allChartData = state.chartData)
                }



                // Description
                coin.description?.takeIf { it.isNotBlank() }?.let { desc ->
                    item {
                        SectionTitle("Description")
                        Text(
                            text = desc,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                // Market Information
                item {
                    SectionTitle("Market Data")
                    MarketInfoItem("Current Price (USD)", coin.currentPriceUSD)
                    MarketInfoItem("Market Cap (USD)", coin.marketCapUSD)
                    MarketInfoItem("Total Volume (USD)", coin.totalVolumeUSD)
                }

                // Technical Information
                item {
                    SectionTitle("Technical Details")
                    InfoRow("Hashing Algorithm", coin.hashingAlgorithm)
                    InfoRow("Genesis Date", coin.genesisDate)
                }

                // Homepage Link
                coin.homepageUrl?.takeIf { it.isNotBlank() }?.let { url ->
                    item {
                        SectionTitle("Website")
                        Text(
                            text = url,
                            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary)
                        )
                    }
                }
            }
        }
        if(!state.error.isNullOrBlank()) {

                Text(
                    text = state.error ?: "Unknown error",
                    color = MaterialTheme.colorScheme.error,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    modifier = androidx.compose.ui.Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(androidx.compose.ui.Alignment.Center)
                )

        }

        if(state.isLoading) {
            CircularProgressIndicator(modifier = androidx.compose.ui.Modifier.align(androidx.compose.ui.Alignment.Center))
        }
    }
}
