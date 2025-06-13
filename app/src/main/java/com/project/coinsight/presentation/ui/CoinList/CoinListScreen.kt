package com.project.coinsight.presentation.ui.CoinList

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.project.coinsight.domain.model.Coin
import com.project.coinsight.extras.isNetworkAvailable
import com.project.coinsight.presentation.ui.SearchCoin.SearchBar
import com.project.coinsight.presentation.ui.SearchCoin.SearchCoinViewModel
import com.project.coinsight.presentation.ui.SearchCoin.SearchResultItem

@Composable
fun CoinListScreen(
    paddingValues: PaddingValues,
    onNavigateToCoinDetails: (String) -> Unit
){
    val coinListViewModel: CoinListViewModel = hiltViewModel<CoinListViewModel>()
    val state = coinListViewModel.uiState.collectAsState().value

    val searchCoinViewModel: SearchCoinViewModel = hiltViewModel<SearchCoinViewModel>()
    val searchState = searchCoinViewModel.uiState.collectAsState().value
    val query = remember { mutableStateOf("") }

    var isConnected by remember { mutableStateOf(true) }
    val context = LocalContext.current

    val isInitialLoading = query.value.isEmpty() && state.isLoading && state.coins.isEmpty()
    val isRefreshing = query.value.isEmpty() && state.isLoading && state.coins.isNotEmpty()

    val isSearchInitialLoading = query.value.isNotEmpty() && searchState.isLoading && searchState.searchResults.isEmpty()
    val isSearchRefreshing = query.value.isNotEmpty() && searchState.isLoading && searchState.searchResults.isNotEmpty()

    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = if (query.value.isEmpty()) isRefreshing else isSearchRefreshing
    )

    LaunchedEffect(isConnected) {
        isConnected = context.isNetworkAvailable()
    }

    Log.d("CoinListScreen", "The coins list is of size ${state.coins.size}")

    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.fillMaxSize()) {

            if (!isConnected) {
                Text(
                    text = "No internet connection. Please turn on your data or connect to Wi-Fi.",
                    color = Color.Red,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }

            SearchBar(
                query = query.value,
                onQueryChange = {
                    query.value = it
                    searchCoinViewModel.searchCoins(it)
                },
                onClear = {
                    query.value = ""
                    searchCoinViewModel.clearSearch()
                },
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxWidth()
            )

            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    isConnected = context.isNetworkAvailable()
                    if (isConnected) {
                        if (query.value.isEmpty()) {
                            coinListViewModel.loadCoins()
                        } else {
                            searchCoinViewModel.searchCoins(query.value)
                        }
                    }
                }
            ) {



            if (query.value.isNotEmpty()) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(searchState.searchResults) { coin ->
                        SearchResultItem(
                            searchCoin = coin,
                            onClick = { onNavigateToCoinDetails(coin.id) }
                        )
                    }
                }
                if (searchState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }

                searchState.error?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.coins) { coin ->
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
                    )
                }
            }
        }


        }
        if (isInitialLoading || isSearchInitialLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        }
    }
