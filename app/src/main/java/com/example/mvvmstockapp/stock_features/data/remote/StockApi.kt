package com.example.mvvmstockapp.stock_features.data.remote

import com.example.mvvmstockapp.stock_features.data.remote.dto.CompanyInfoDto
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {


    @GET("/query?function=LISTING_STATUS")
    suspend fun getListings(
        @Query("apikey") apikey: String = API_KEY
    ): ResponseBody


    @GET("/query?function=OVERVIEW")
    suspend fun getCompanyInfo(
        @Query("symbol") symbol: String,
        @Query("apikey") apikey: String = API_KEY
    ): CompanyInfoDto

    @GET("/query?function=TIME_SERIES_INTRADAY&interval=60min&datatype=csv")
    suspend fun getIntradayInfo(
        @Query("symbol") symbol: String,
        @Query("apikey") apikey: String = API_KEY
    ): ResponseBody


    companion object{
        const val API_KEY = "IDGW86T20LJL18KH"
        const val BASE_URL = "https://alphavantage.co"
    }

}