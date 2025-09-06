package com.paymentreminder.app.data.repository

import com.paymentreminder.app.data.dao.CategoryTotal
import com.paymentreminder.app.data.dao.ExpenseDao
import com.paymentreminder.app.data.entity.Expense
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseRepository @Inject constructor(
    private val expenseDao: ExpenseDao
) {
    fun getAllExpenses(): Flow<List<Expense>> = expenseDao.getAllExpenses()
    
    fun getExpensesByDateRange(startDate: Date, endDate: Date): Flow<List<Expense>> = 
        expenseDao.getExpensesByDateRange(startDate, endDate)
    
    suspend fun getExpenseById(id: Long): Expense? = expenseDao.getExpenseById(id)
    
    suspend fun insertExpense(expense: Expense): Long = expenseDao.insertExpense(expense)
    
    suspend fun updateExpense(expense: Expense) = expenseDao.updateExpense(expense)
    
    suspend fun deleteExpense(expense: Expense) = expenseDao.deleteExpense(expense)
    
    suspend fun getTotalExpensesByDateRange(startDate: Date, endDate: Date): Double? = 
        expenseDao.getTotalExpensesByDateRange(startDate, endDate)
    
    suspend fun getExpensesByCategory(startDate: Date, endDate: Date): List<CategoryTotal> = 
        expenseDao.getExpensesByCategory(startDate, endDate)
}