package com.paymentreminder.app.data.dao

import androidx.room.*
import com.paymentreminder.app.data.entity.Payment
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface PaymentDao {
    @Query("SELECT * FROM payments ORDER BY dueDate ASC")
    fun getAllPayments(): Flow<List<Payment>>

    @Query("SELECT * FROM payments WHERE isPaid = 0 ORDER BY dueDate ASC")
    fun getUpcomingPayments(): Flow<List<Payment>>

    @Query("SELECT * FROM payments WHERE dueDate BETWEEN :startDate AND :endDate")
    fun getPaymentsByDateRange(startDate: Date, endDate: Date): Flow<List<Payment>>

    @Query("SELECT * FROM payments WHERE id = :id")
    suspend fun getPaymentById(id: Long): Payment?

    @Insert
    suspend fun insertPayment(payment: Payment): Long

    @Update
    suspend fun updatePayment(payment: Payment)

    @Delete
    suspend fun deletePayment(payment: Payment)

    @Query("UPDATE payments SET isPaid = :isPaid WHERE id = :id")
    suspend fun updatePaymentStatus(id: Long, isPaid: Boolean)

    @Query("SELECT * FROM payments WHERE dueDate <= :date AND isPaid = 0")
    fun getOverduePayments(date: Date): Flow<List<Payment>>
}