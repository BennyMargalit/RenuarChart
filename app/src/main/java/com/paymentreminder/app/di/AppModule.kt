package com.paymentreminder.app.di

import com.paymentreminder.app.camera.CameraManager
import com.paymentreminder.app.camera.ReceiptParser
import com.paymentreminder.app.notification.NotificationManager
import com.paymentreminder.app.notification.NotificationScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCameraManager(): CameraManager {
        return CameraManager()
    }

    @Provides
    @Singleton
    fun provideReceiptParser(): ReceiptParser {
        return ReceiptParser()
    }

    @Provides
    @Singleton
    fun provideNotificationManager(@ApplicationContext context: android.content.Context): NotificationManager {
        return NotificationManager(context)
    }

    @Provides
    @Singleton
    fun provideNotificationScheduler(@ApplicationContext context: android.content.Context): NotificationScheduler {
        return NotificationScheduler(context)
    }
}