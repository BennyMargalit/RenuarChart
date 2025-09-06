package com.paymentreminder.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymentreminder.app.data.dao.CategoryTotal
import com.paymentreminder.app.data.entity.Expense
import com.paymentreminder.app.data.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository
) : ViewModel() {
    
    private val _expenses = MutableStateFlow<List<Expense>>(emptyList())
    val expenses: StateFlow<List<Expense>> = _expenses.asStateFlow()
    
    private val _monthlyExpenses = MutableStateFlow<List<Expense>>(emptyList())
    val monthlyExpenses: StateFlow<List<Expense>> = _monthlyExpenses.asStateFlow()
    
    private val _categoryTotals = MutableStateFlow<List<CategoryTotal>>(emptyList())
    val categoryTotals: StateFlow<List<CategoryTotal>> = _categoryTotals.asStateFlow()
    
    private val _totalMonthlyExpense = MutableStateFlow(0.0)
    val totalMonthlyExpense: StateFlow<Double> = _totalMonthlyExpense.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    init {
        loadExpenses()
        loadCurrentMonthExpenses()
    }
    
    private fun loadExpenses() {
        viewModelScope.launch {
            expenseRepository.getAllExpenses().collect { expensesList ->
                _expenses.value = expensesList
            }
        }
    }
    
    fun loadCurrentMonthExpenses() {
        viewModelScope.launch {
            val calendar = Calendar.getInstance()
            val startOfMonth = calendar.apply {
                set(Calendar.DAY_OF_MONTH, 1)
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.time
            
            val endOfMonth = calendar.apply {
                set(Calendar.DAY_OF_MONTH, getActualMaximum(Calendar.DAY_OF_MONTH))
                set(Calendar.HOUR_OF_DAY, 23)
                set(Calendar.MINUTE, 59)
                set(Calendar.SECOND, 59)
                set(Calendar.MILLISECOND, 999)
            }.time
            
            expenseRepository.getExpensesByDateRange(startOfMonth, endOfMonth).collect { expensesList ->
                _monthlyExpenses.value = expensesList
            }
            
            val total = expenseRepository.getTotalExpensesByDateRange(startOfMonth, endOfMonth)
            _totalMonthlyExpense.value = total ?: 0.0
            
            val categories = expenseRepository.getExpensesByCategory(startOfMonth, endOfMonth)
            _categoryTotals.value = categories
        }
    }
    
    fun addExpense(expense: Expense) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                expenseRepository.insertExpense(expense)
                loadCurrentMonthExpenses() // Refresh monthly data
            } catch (e: Exception) {
                // Handle error
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun updateExpense(expense: Expense) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                expenseRepository.updateExpense(expense)
                loadCurrentMonthExpenses() // Refresh monthly data
            } catch (e: Exception) {
                // Handle error
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                expenseRepository.deleteExpense(expense)
                loadCurrentMonthExpenses() // Refresh monthly data
            } catch (e: Exception) {
                // Handle error
            } finally {
                _isLoading.value = false
            }
        }
    }
}