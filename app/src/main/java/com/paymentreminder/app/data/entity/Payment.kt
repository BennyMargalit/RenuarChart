package com.paymentreminder.app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "payments")
data class Payment(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val amount: Double,
    val dueDate: Date,
    val category: String,
    val description: String? = null,
    val isPaid: Boolean = false,
    val isRecurring: Boolean = false,
    val recurringInterval: String? = null, // "monthly", "yearly", etc.
    val createdAt: Date = Date(),
    val receiptImagePath: String? = null
)