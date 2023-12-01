package com.example.mvvmstockapp.stock_features.data.mapper

import com.example.mvvmstockapp.stock_features.data.local.entities.CompanyListingEntity
import com.example.mvvmstockapp.stock_features.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing(): CompanyListing{
    return CompanyListing(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}


fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity{
    return CompanyListingEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}