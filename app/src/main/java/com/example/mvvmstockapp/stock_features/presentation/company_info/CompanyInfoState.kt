package com.example.mvvmstockapp.stock_features.presentation.company_info

import com.example.mvvmstockapp.stock_features.domain.model.CompanyInfo
import com.example.mvvmstockapp.stock_features.domain.model.IntradayInfo

data class CompanyInfoState(
    val stocksInfo: List<IntradayInfo> = emptyList(),
    val company: CompanyInfo? = null,
    val isLoading: Boolean = false
)
