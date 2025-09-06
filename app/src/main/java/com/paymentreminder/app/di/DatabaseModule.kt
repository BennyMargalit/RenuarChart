package com.paymentreminder.app.di

import android.content.Context
import com.paymentreminder.app.data.dao.ExpenseDao
import com.paymentreminder.app.data.dao.PaymentDao
import com.paymentreminder.app.data.database.PaymentDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PaymentDatabase {
        return PaymentDatabase.getDatabase(context)
    }

    @Provides
    fun providePaymentDao(database: PaymentDatabase): PaymentDao {
        return database.paymentDao()
    }

    @Provides
    fun provideExpenseDao(database: PaymentDatabase): ExpenseDao {
        return database.expenseDao()
    }
}