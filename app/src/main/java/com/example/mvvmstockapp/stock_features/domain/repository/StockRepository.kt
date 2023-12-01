package com.example.mvvmstockapp.stock_features.domain.repository

import com.example.mvvmstockapp.core.utils.Resources
import com.example.mvvmstockapp.stock_features.domain.model.CompanyListing
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListing(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resources<List<CompanyListing>>>

}