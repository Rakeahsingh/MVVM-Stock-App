package com.example.mvvmstockapp.stock_features.data.remote.dto



import com.squareup.moshi.Json

data class CompanyInfoDto(
    @field:Json(name = "Symbol") val symbol: String?,
    @field:Json(name = "Name") val name: String?,
    @field:Json(name = "Description") val description: String?,
    @field:Json(name = "Currency") val currency: String?,
    @field:Json(name = "Country") val country: String?,
    @field:Json(name = "Sector") val sector: String?,
    @field:Json(name = "Industry") val industry: String?,
)
