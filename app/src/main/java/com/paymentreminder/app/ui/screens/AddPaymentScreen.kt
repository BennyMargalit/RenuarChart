package com.paymentreminder.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.paymentreminder.app.data.entity.Payment
import com.paymentreminder.app.ui.viewmodel.PaymentViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPaymentScreen(
    navController: NavController,
    paymentViewModel: PaymentViewModel = hiltViewModel()
) {
    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var dueDate by remember { mutableStateOf(Date()) }
    var isRecurring by remember { mutableStateOf(false) }
    var recurringInterval by remember { mutableStateOf("monthly") }
    
    val categories = listOf(
        "Utilities", "Rent", "Insurance", "Loan", "Credit Card",
        "Phone", "Internet", "Subscription", "Other"
    )
    
    val recurringIntervals = listOf("monthly", "quarterly", "yearly")
    
    var showDatePicker by remember { mutableStateOf(false) }
    var showCategoryDropdown by remember { mutableStateOf(false) }
    var showRecurringDropdown by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Payment") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Payment Title *") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Title, contentDescription = null) }
            )
            
            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount *") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.AttachMoney, contentDescription = null) },
                supportingText = { Text("Enter amount in dollars") }
            )
            
            // Category dropdown
            ExposedDropdownMenuBox(
                expanded = showCategoryDropdown,
                onExpandedChange = { showCategoryDropdown = !showCategoryDropdown }
            ) {
                OutlinedTextField(
                    value = category,
                    onValueChange = { },
                    readOnly = true,
                    label = { Text("Category *") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showCategoryDropdown) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    leadingIcon = { Icon(Icons.Default.Category, contentDescription = null) }
                )
                ExposedDropdownMenu(
                    expanded = showCategoryDropdown,
                    onDismissRequest = { showCategoryDropdown = false }
                ) {
                    categories.forEach { categoryOption ->
                        DropdownMenuItem(
                            text = { Text(categoryOption) },
                            onClick = {
                                category = categoryOption
                                showCategoryDropdown = false
                            }
                        )
                    }
                }
            }
            
            // Due date picker
            OutlinedTextField(
                value = java.text.SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(dueDate),
                onValueChange = { },
                readOnly = true,
                label = { Text("Due Date *") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(Icons.Default.CalendarToday, contentDescription = "Select Date")
                    }
                }
            )
            
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2,
                maxLines = 4,
                leadingIcon = { Icon(Icons.Default.Description, contentDescription = null) }
            )
            
            // Recurring payment options
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Recurring Payment",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Medium
                        )
                        Switch(
                            checked = isRecurring,
                            onCheckedChange = { isRecurring = it }
                        )
                    }
                    
                    if (isRecurring) {
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        ExposedDropdownMenuBox(
                            expanded = showRecurringDropdown,
                            onExpandedChange = { showRecurringDropdown = !showRecurringDropdown }
                        ) {
                            OutlinedTextField(
                                value = recurringInterval,
                                onValueChange = { },
                                readOnly = true,
                                label = { Text("Recurring Interval") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showRecurringDropdown) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor()
                            )
                            ExposedDropdownMenu(
                                expanded = showRecurringDropdown,
                                onDismissRequest = { showRecurringDropdown = false }
                            ) {
                                recurringIntervals.forEach { interval ->
                                    DropdownMenuItem(
                                        text = { Text(interval.capitalize()) },
                                        onClick = {
                                            recurringInterval = interval
                                            showRecurringDropdown = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = {
                    if (title.isNotEmpty() && amount.isNotEmpty() && category.isNotEmpty()) {
                        val payment = Payment(
                            title = title,
                            amount = amount.toDoubleOrNull() ?: 0.0,
                            dueDate = dueDate,
                            category = category,
                            description = description.takeIf { it.isNotEmpty() },
                            isRecurring = isRecurring,
                            recurringInterval = if (isRecurring) recurringInterval else null
                        )
                        paymentViewModel.addPayment(payment)
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = title.isNotEmpty() && amount.isNotEmpty() && category.isNotEmpty()
            ) {
                Text("Add Payment")
            }
        }
    }
    
    // Date picker dialog
    if (showDatePicker) {
        // This would need a proper date picker implementation
        // For now, we'll use a simple approach
        AlertDialog(
            onDismissRequest = { showDatePicker = false },
            title = { Text("Select Due Date") },
            text = { Text("Date picker would be implemented here") },
            confirmButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("OK")
                }
            }
        )
    }
}