package com.example.mvvmstockapp.di

import com.example.mvvmstockapp.stock_features.data.csv.CSVParser
import com.example.mvvmstockapp.stock_features.data.csv.CompanyListingsParser
import com.example.mvvmstockapp.stock_features.data.csv.IntradayInfoParser
import com.example.mvvmstockapp.stock_features.data.repository.StockRepositoryImpl
import com.example.mvvmstockapp.stock_features.domain.model.CompanyListing
import com.example.mvvmstockapp.stock_features.domain.model.IntradayInfo
import com.example.mvvmstockapp.stock_features.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsCompanyListingParser(
        companyListingsParser: CompanyListingsParser
    ): CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindIntradayInfoParser(
        intradayInfoParser: IntradayInfoParser
    ): CSVParser<IntradayInfo>

    @Binds
    @Singleton
    abstract fun bindsRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository

}