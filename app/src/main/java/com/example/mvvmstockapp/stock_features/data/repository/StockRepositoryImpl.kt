package com.example.mvvmstockapp.stock_features.data.repository

import com.example.mvvmstockapp.core.utils.Resources
import com.example.mvvmstockapp.stock_features.data.csv.CSVParser
import com.example.mvvmstockapp.stock_features.data.local.StockDao
import com.example.mvvmstockapp.stock_features.data.local.StockDatabase
import com.example.mvvmstockapp.stock_features.data.mapper.toCompanyInfo
import com.example.mvvmstockapp.stock_features.data.mapper.toCompanyListing
import com.example.mvvmstockapp.stock_features.data.mapper.toCompanyListingEntity
import com.example.mvvmstockapp.stock_features.data.remote.StockApi
import com.example.mvvmstockapp.stock_features.domain.model.CompanyInfo
import com.example.mvvmstockapp.stock_features.domain.model.CompanyListing
import com.example.mvvmstockapp.stock_features.domain.model.IntradayInfo
import com.example.mvvmstockapp.stock_features.domain.repository.StockRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class StockRepositoryImpl @Inject constructor(
    private val api: StockApi,
    private val db: StockDatabase,
    private val companyListingsParser: CSVParser<CompanyListing>,
    private val intradayInfoParser: CSVParser<IntradayInfo>
): StockRepository {

    private val dao = db.dao
    override suspend fun getCompanyListing(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resources<List<CompanyListing>>> {
        return flow {
            emit(Resources.Loading(true))
            val localListings = dao.searchCompanyListing(query)
            emit(Resources.Success(
                data = localListings.map {
                    it.toCompanyListing()
                }
            ))

            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if(shouldJustLoadFromCache) {
                emit(Resources.Loading(false))
                return@flow
            }

             val remoteListing = try {
                val response = api.getListings()
                companyListingsParser.parse(response.byteStream())

            } catch(e: IOException) {
                e.printStackTrace()
                emit(Resources.Error(
                    message = "Couldn't be load"
                ))
                 null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resources.Error(
                    message = "Couldn't be load"
                ))
                 null
            }

            remoteListing?.let { listing ->
                dao.clearCompanyListing()
                dao.insertCompanyListing(
                    listing.map {
                        it.toCompanyListingEntity()
                    }
                )
                emit(Resources.Success(
                    data = dao.searchCompanyListing("").map {
                        it.toCompanyListing()
                    }
                ))
                emit(Resources.Loading(false))
            }

        }
    }

    override suspend fun getIntradayInfo(symbol: String): Resources<List<IntradayInfo>> {
        return try {
            val response = api.getIntradayInfo(symbol)
            val result = intradayInfoParser.parse(response.byteStream())
            Resources.Success(
                data = result
            )
        }catch (e: IOException){
            e.printStackTrace()
            Resources.Error(
                message = "Couldn't load Intarday Info"
            )
        }catch (e: HttpException){
            e.printStackTrace()
            Resources.Error(
                message = "Couldn't load Intraday Info"
            )
        }
    }

    override suspend fun getCompanyInfo(symbol: String): Resources<CompanyInfo> {
        return try {
            val result = api.getCompanyInfo(symbol)
            Resources.Success(
                data = result.toCompanyInfo()
            )
        }catch (e: IOException){
            e.printStackTrace()
            Resources.Error(
                message = "Couldn't load Company Info"
            )
        }catch (e: HttpException){
            e.printStackTrace()
            Resources.Error(
                message = "Couldn't load Company Info"
            )
        }
    }
}