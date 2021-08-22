package com.hieuwu.groceriesstore.di

import android.content.Context
import androidx.room.Room
import com.hieuwu.groceriesstore.data.GroceriesStoreDatabase
import com.hieuwu.groceriesstore.data.dao.LineItemDao
import com.hieuwu.groceriesstore.data.dao.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): GroceriesStoreDatabase {
        return Room.databaseBuilder(
            appContext,
            GroceriesStoreDatabase::class.java,
            "groceries_store.db"
        ).build()
    }

    @Provides
    fun provideProductDao(database: GroceriesStoreDatabase): ProductDao {
        return database.productDao()
    }

    @Provides
    fun provideLineItemDao(database: GroceriesStoreDatabase): LineItemDao {
        return database.lineItemDao()
    }
}