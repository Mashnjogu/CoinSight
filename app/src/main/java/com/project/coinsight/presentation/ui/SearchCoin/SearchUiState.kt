package com.project.coinsight.presentation.ui.SearchCoin

import com.project.coinsight.domain.model.SearchCoin

data class SearchUiState(
    val searchResults: List<SearchCoin> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
