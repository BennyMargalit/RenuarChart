package com.paymentreminder.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymentreminder.app.data.entity.Payment
import com.paymentreminder.app.data.repository.PaymentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val paymentRepository: PaymentRepository
) : ViewModel() {
    
    private val _payments = MutableStateFlow<List<Payment>>(emptyList())
    val payments: StateFlow<List<Payment>> = _payments.asStateFlow()
    
    private val _upcomingPayments = MutableStateFlow<List<Payment>>(emptyList())
    val upcomingPayments: StateFlow<List<Payment>> = _upcomingPayments.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    init {
        loadPayments()
        loadUpcomingPayments()
    }
    
    private fun loadPayments() {
        viewModelScope.launch {
            paymentRepository.getAllPayments().collect { paymentsList ->
                _payments.value = paymentsList
            }
        }
    }
    
    private fun loadUpcomingPayments() {
        viewModelScope.launch {
            paymentRepository.getUpcomingPayments().collect { paymentsList ->
                _upcomingPayments.value = paymentsList
            }
        }
    }
    
    fun addPayment(payment: Payment) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                paymentRepository.insertPayment(payment)
            } catch (e: Exception) {
                // Handle error
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun updatePayment(payment: Payment) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                paymentRepository.updatePayment(payment)
            } catch (e: Exception) {
                // Handle error
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun markAsPaid(paymentId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                paymentRepository.updatePaymentStatus(paymentId, true)
            } catch (e: Exception) {
                // Handle error
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun deletePayment(payment: Payment) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                paymentRepository.deletePayment(payment)
            } catch (e: Exception) {
                // Handle error
            } finally {
                _isLoading.value = false
            }
        }
    }
}