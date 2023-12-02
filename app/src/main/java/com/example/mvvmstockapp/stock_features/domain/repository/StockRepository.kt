package com.example.mvvmstockapp.stock_features.domain.repository

import com.example.mvvmstockapp.core.utils.Resources
import com.example.mvvmstockapp.stock_features.domain.model.CompanyInfo
import com.example.mvvmstockapp.stock_features.domain.model.CompanyListing
import com.example.mvvmstockapp.stock_features.domain.model.IntradayInfo
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListing(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resources<List<CompanyListing>>>

    suspend fun getCompanyInfo(
        symbol: String
    ): Resources<CompanyInfo>

    suspend fun getIntradayInfo(
        symbol: String
    ): Resources<List<IntradayInfo>>

}