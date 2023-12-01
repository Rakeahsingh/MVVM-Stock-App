package com.example.mvvmstockapp.stock_features.data.repository

import com.example.mvvmstockapp.core.utils.Resources
import com.example.mvvmstockapp.stock_features.data.csv.CSVParser
import com.example.mvvmstockapp.stock_features.data.local.StockDao
import com.example.mvvmstockapp.stock_features.data.mapper.toCompanyListing
import com.example.mvvmstockapp.stock_features.data.mapper.toCompanyListingEntity
import com.example.mvvmstockapp.stock_features.data.remote.StockApi
import com.example.mvvmstockapp.stock_features.domain.model.CompanyListing
import com.example.mvvmstockapp.stock_features.domain.repository.StockRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class StockRepositoryImpl @Inject constructor(
    private val api: StockApi,
    private val dao: StockDao,
    private val companyListingsParser: CSVParser<CompanyListing>
): StockRepository {
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
}