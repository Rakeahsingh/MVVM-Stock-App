package com.example.mvvmstockapp.stock_features.data.mapper

import com.example.mvvmstockapp.stock_features.data.remote.dto.CompanyInfoDto
import com.example.mvvmstockapp.stock_features.domain.model.CompanyInfo

fun CompanyInfoDto.toCompanyInfo(): CompanyInfo{
    return CompanyInfo(
        symbol = symbol ?: "",
        name = name ?: "",
        description = description ?: "",
        currency = currency ?: "",
        country = country ?: "",
        sector = sector ?: "",
        industry = industry ?: ""
    )
}