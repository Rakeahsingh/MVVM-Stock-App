package com.example.mvvmstockapp.stock_features.domain.model


data class CompanyInfo(
    val symbol: String,
    val name: String,
    val description: String,
    val currency: String,
    val country: String,
    val sector: String,
    val industry: String,
)
