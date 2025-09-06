package com.paymentreminder.app.data.dao

import androidx.room.*
import com.paymentreminder.app.data.entity.Expense
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expenses ORDER BY date DESC")
    fun getAllExpenses(): Flow<List<Expense>>

    @Query("SELECT * FROM expenses WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getExpensesByDateRange(startDate: Date, endDate: Date): Flow<List<Expense>>

    @Query("SELECT * FROM expenses WHERE id = :id")
    suspend fun getExpenseById(id: Long): Expense?

    @Insert
    suspend fun insertExpense(expense: Expense): Long

    @Update
    suspend fun updateExpense(expense: Expense)

    @Delete
    suspend fun deleteExpense(expense: Expense)

    @Query("SELECT SUM(amount) FROM expenses WHERE date BETWEEN :startDate AND :endDate")
    suspend fun getTotalExpensesByDateRange(startDate: Date, endDate: Date): Double?

    @Query("SELECT category, SUM(amount) as total FROM expenses WHERE date BETWEEN :startDate AND :endDate GROUP BY category")
    suspend fun getExpensesByCategory(startDate: Date, endDate: Date): List<CategoryTotal>
}

data class CategoryTotal(
    val category: String,
    val total: Double
)