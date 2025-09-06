package com.paymentreminder.app.ui.screens

import android.net.Uri
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.paymentreminder.app.camera.CameraManager
import com.paymentreminder.app.camera.ReceiptData
import com.paymentreminder.app.camera.ReceiptParser
import com.paymentreminder.app.ui.components.ReceiptPreviewDialog

@Composable
fun CameraScreen(
    navController: NavController
) {
    val cameraManager = hiltViewModel<CameraManager>()
    val receiptParser = hiltViewModel<ReceiptParser>()
    val lifecycleOwner = LocalLifecycleOwner.current
    var capturedImageUri by remember { mutableStateOf<Uri?>(null) }
    var extractedText by remember { mutableStateOf("") }
    var parsedReceipt by remember { mutableStateOf<ReceiptData?>(null) }
    var isProcessing by remember { mutableStateOf(false) }
    var showPreview by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        // Camera initialization would go here
        // cameraManager.startCamera(context, previewView, lifecycleOwner)
    }
    
    DisposableEffect(Unit) {
        onDispose {
            cameraManager.cleanup()
        }
    }
    
    Box(modifier = Modifier.fillMaxSize()) {
        // Camera preview placeholder
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Default.CameraAlt,
                    contentDescription = "Camera",
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Camera Preview",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Camera integration would be implemented here",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        
        // Camera controls
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .size(56.dp)
                        .background(
                            MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                            CircleShape
                        )
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Close",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
                
                FloatingActionButton(
                    onClick = {
                        isProcessing = true
                        // Simulate image capture and processing
                        LaunchedEffect(Unit) {
                            // Simulate processing delay
                            kotlinx.coroutines.delay(2000)
                            val mockReceiptData = ReceiptData(
                                amount = 25.99,
                                merchant = "Sample Store",
                                date = "12/15/2023",
                                rawText = "Sample Store\n123 Main St\nTotal: $25.99\nDate: 12/15/2023"
                            )
                            parsedReceipt = mockReceiptData
                            showPreview = true
                            isProcessing = false
                        }
                    },
                    modifier = Modifier.size(72.dp)
                ) {
                    Icon(
                        Icons.Default.CameraAlt,
                        contentDescription = "Capture",
                        modifier = Modifier.size(32.dp)
                    )
                }
                
                IconButton(
                    onClick = { /* Gallery picker logic */ },
                    modifier = Modifier
                        .size(56.dp)
                        .background(
                            MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                            CircleShape
                        )
                ) {
                    Icon(
                        Icons.Default.PhotoLibrary,
                        contentDescription = "Gallery",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
        
        // Processing indicator
        if (isProcessing) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Processing receipt...")
                }
            }
        }
    }
    
    // Receipt preview dialog
    if (showPreview && parsedReceipt != null) {
        ReceiptPreviewDialog(
            receiptData = parsedReceipt!!,
            onConfirm = { receiptData ->
                // Navigate to add expense screen with pre-filled data
                navController.navigate("add_expense?amount=${receiptData.amount}&merchant=${receiptData.merchant}")
            },
            onDismiss = { showPreview = false }
        )
    }
}
