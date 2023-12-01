package com.example.mvvmstockapp.stock_features.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mvvmstockapp.stock_features.data.local.entities.CompanyListingEntity


@Database(
    entities = [CompanyListingEntity::class],
    version = 1
)
abstract class StockDatabase: RoomDatabase() {

    abstract val dao: StockDao

}