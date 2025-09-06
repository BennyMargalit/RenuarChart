package com.paymentreminder.app.data.repository

import com.paymentreminder.app.data.dao.PaymentDao
import com.paymentreminder.app.data.entity.Payment
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PaymentRepository @Inject constructor(
    private val paymentDao: PaymentDao
) {
    fun getAllPayments(): Flow<List<Payment>> = paymentDao.getAllPayments()
    
    fun getUpcomingPayments(): Flow<List<Payment>> = paymentDao.getUpcomingPayments()
    
    fun getPaymentsByDateRange(startDate: Date, endDate: Date): Flow<List<Payment>> = 
        paymentDao.getPaymentsByDateRange(startDate, endDate)
    
    suspend fun getPaymentById(id: Long): Payment? = paymentDao.getPaymentById(id)
    
    suspend fun insertPayment(payment: Payment): Long = paymentDao.insertPayment(payment)
    
    suspend fun updatePayment(payment: Payment) = paymentDao.updatePayment(payment)
    
    suspend fun deletePayment(payment: Payment) = paymentDao.deletePayment(payment)
    
    suspend fun updatePaymentStatus(id: Long, isPaid: Boolean) = 
        paymentDao.updatePaymentStatus(id, isPaid)
    
    fun getOverduePayments(date: Date): Flow<List<Payment>> = 
        paymentDao.getOverduePayments(date)
}