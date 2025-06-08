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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CoinDetailScreen(
    coinId: String,
    onNavigateToCoinDetails: (String) -> Unit
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
                contentPadding = PaddingValues(20.dp)
            ) {
                item {
                    Row(
                        modifier = androidx.compose.ui.Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${coin.marketCapRank}. ${coin.name} ",
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = androidx.compose.ui.Modifier.weight(8f)
                        )
                    }

                    Spacer(modifier = androidx.compose.ui.Modifier.height(15.dp))
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
