package com.paymentreminder.app.camera

import android.content.Context
import android.net.Uri
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.tasks.await
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CameraManager @Inject constructor() {
    private var imageCapture: ImageCapture? = null
    private lateinit var cameraExecutor: ExecutorService
    private val textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    fun startCamera(
        context: Context,
        previewView: PreviewView,
        lifecycleOwner: LifecycleOwner
    ) {
        cameraExecutor = Executors.newSingleThreadExecutor()
        
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

            imageCapture = ImageCapture.Builder().build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview,
                    imageCapture
                )
            } catch (exc: Exception) {
                // Handle camera binding error
            }
        }, ContextCompat.getMainExecutor(context))
    }

    suspend fun captureImage(context: Context): Uri? {
        val imageCapture = imageCapture ?: return null

        val photoFile = File(
            context.getExternalFilesDir(null),
            SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.US)
                .format(System.currentTimeMillis()) + ".jpg"
        )

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        return try {
            imageCapture.takePicture(outputOptions, cameraExecutor) { result ->
                // Image captured successfully
            }.await()
            Uri.fromFile(photoFile)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun extractTextFromImage(context: Context, imageUri: Uri): String {
        return try {
            val image = InputImage.fromFilePath(context, imageUri)
            val result = textRecognizer.process(image).await()
            result.text
        } catch (e: Exception) {
            ""
        }
    }

    fun cleanup() {
        cameraExecutor.shutdown()
        textRecognizer.close()
    }
}