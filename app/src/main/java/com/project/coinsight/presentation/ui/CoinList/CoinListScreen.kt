package com.project.coinsight.presentation.ui.CoinList

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.coinsight.domain.model.Coin
import com.project.coinsight.presentation.ui.SearchCoin.SearchCoinViewModel

@Composable
fun CoinListScreen(
    paddingValues: PaddingValues,
    onNavigateToCoinDetails: (String) -> Unit
){
    val coinListViewModel: CoinListViewModel = hiltViewModel<CoinListViewModel>()
    val state = coinListViewModel.uiState.collectAsState().value

    val searchListViewModel: SearchCoinViewModel = hiltViewModel<SearchCoinViewModel>()
    val searchState = searchListViewModel.uiState.collectAsState().value
    val query = remember { mutableStateOf("") }



    Log.d("CoinListScreen", "The coins list is of size ${state.coins.size}")

    Box(modifier = Modifier.fillMaxSize()){
        LazyColumn(modifier = Modifier.fillMaxSize()){
            items(state.coins){ coin ->
                CoinListItem(coin = coin, onItemClick = onNavigateToCoinDetails)
            }
        }
        state.error?.takeIf { it.isNotBlank() }?.let {
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }
        if(state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}


@Composable
fun CoinListItem(coin: Coin, onItemClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(coin.id) }
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${coin.marketCapRank}. ${coin.name} (${coin.symbol})",
            style = MaterialTheme.typography.bodyLarge,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.width(8.dp))
//        Text(
//            text = if(coin.) "active" else "inactive",
//            style = MaterialTheme.typography.bodyMedium,
//            textAlign = TextAlign.End
//        )
    }
}