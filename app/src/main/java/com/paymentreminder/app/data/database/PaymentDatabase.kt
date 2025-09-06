package com.paymentreminder.app.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.paymentreminder.app.data.converters.DateConverters
import com.paymentreminder.app.data.dao.ExpenseDao
import com.paymentreminder.app.data.dao.PaymentDao
import com.paymentreminder.app.data.entity.Expense
import com.paymentreminder.app.data.entity.Payment

@Database(
    entities = [Payment::class, Expense::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverters::class)
abstract class PaymentDatabase : RoomDatabase() {
    abstract fun paymentDao(): PaymentDao
    abstract fun expenseDao(): ExpenseDao

    companion object {
        @Volatile
        private var INSTANCE: PaymentDatabase? = null

        fun getDatabase(context: Context): PaymentDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PaymentDatabase::class.java,
                    "payment_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}