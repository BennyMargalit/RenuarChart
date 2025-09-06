package com.paymentreminder.app.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.paymentreminder.app.MainActivity
import com.paymentreminder.app.R
import com.paymentreminder.app.data.entity.Payment
import com.paymentreminder.app.data.repository.PaymentRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class PaymentReminderWorker @HiltWorker constructor(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    @Inject
    lateinit var paymentRepository: PaymentRepository

    override suspend fun doWork(): Result {
        val today = Calendar.getInstance()
        val tomorrow = Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, 1) }
        
        val upcomingPayments = paymentRepository.getUpcomingPayments().first()
        val overduePayments = paymentRepository.getOverduePayments(today.time).first()
        
        val notificationManager = NotificationManager(applicationContext)
        
        // Show overdue payments
        overduePayments.forEach { payment ->
            notificationManager.showOverdueNotification(payment)
        }
        
        // Show upcoming payments (due tomorrow)
        upcomingPayments.filter { payment ->
            val paymentDate = Calendar.getInstance().apply { time = payment.dueDate }
            paymentDate.get(Calendar.DAY_OF_YEAR) == tomorrow.get(Calendar.DAY_OF_YEAR) &&
            paymentDate.get(Calendar.YEAR) == tomorrow.get(Calendar.YEAR)
        }.forEach { payment ->
            notificationManager.showUpcomingNotification(payment)
        }
        
        return Result.success()
    }
}

class NotificationManager(private val context: Context) {
    
    init {
        createNotificationChannel()
    }
    
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Payment Reminders",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for upcoming and overdue payments"
            }
            
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    
    fun showUpcomingNotification(payment: Payment) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Payment Due Tomorrow")
            .setContentText("${payment.title} - $${String.format("%.2f", payment.amount)}")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(payment.id.toInt(), notification)
    }
    
    fun showOverdueNotification(payment: Payment) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Payment Overdue")
            .setContentText("${payment.title} - $${String.format("%.2f", payment.amount)}")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(payment.id.toInt(), notification)
    }
    
    companion object {
        private const val CHANNEL_ID = "payment_reminders"
    }
}

class NotificationScheduler(private val context: Context) {
    
    fun scheduleDailyReminders() {
        val workRequest = PeriodicWorkRequestBuilder<PaymentReminderWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(calculateInitialDelay(), TimeUnit.MILLISECONDS)
            .build()
        
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "payment_reminders",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
    
    private fun calculateInitialDelay(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 9) // 9 AM
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        
        val now = Calendar.getInstance()
        if (calendar.timeInMillis <= now.timeInMillis) {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        
        return calendar.timeInMillis - now.timeInMillis
    }
}