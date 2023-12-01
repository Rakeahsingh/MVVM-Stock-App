package com.example.mvvmstockapp.stock_features.presentation.company_listings

import com.example.mvvmstockapp.stock_features.domain.model.CompanyListing

data class CompanyListingState(
    val companies: List<CompanyListing> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false
)
