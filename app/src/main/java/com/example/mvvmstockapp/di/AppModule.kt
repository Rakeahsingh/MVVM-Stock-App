package com.example.mvvmstockapp.di

import android.app.Application
import androidx.room.Room
import com.example.mvvmstockapp.stock_features.data.csv.CompanyListingsParser
import com.example.mvvmstockapp.stock_features.data.local.StockDatabase
import com.example.mvvmstockapp.stock_features.data.remote.StockApi
import com.example.mvvmstockapp.stock_features.data.repository.StockRepositoryImpl
import com.example.mvvmstockapp.stock_features.domain.repository.StockRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClint(): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    @Singleton
    fun provideStockApi(client: OkHttpClient): StockApi{
        return Retrofit.Builder()
            .baseUrl(StockApi.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StockApi::class.java)
    }

    @Provides
    @Singleton
    fun provideStockDatabase(app: Application): StockDatabase{
        return Room.databaseBuilder(
            app,
            StockDatabase::class.java,
            "Stock_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideStockRepository(
        api: StockApi,
        db: StockDatabase,
        companyListingsParser: CompanyListingsParser
    ): StockRepository{
        return StockRepositoryImpl(api, db.dao, companyListingsParser)
    }

}