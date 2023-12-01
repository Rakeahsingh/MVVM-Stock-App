package com.example.mvvmstockapp.stock_features.presentation.company_listings

sealed class CompanyListingEvent{

    object Refresh: CompanyListingEvent()

    data class SearchQueryChange(val query: String): CompanyListingEvent()

}
