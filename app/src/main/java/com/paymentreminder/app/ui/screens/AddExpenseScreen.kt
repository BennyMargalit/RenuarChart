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
import com.paymentreminder.app.data.entity.Expense
import com.paymentreminder.app.ui.viewmodel.ExpenseViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(
    navController: NavController,
    expenseViewModel: ExpenseViewModel = hiltViewModel()
) {
    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var expenseDate by remember { mutableStateOf(Date()) }
    
    val categories = listOf(
        "Food & Dining", "Transportation", "Shopping", "Entertainment",
        "Healthcare", "Education", "Utilities", "Travel", "Other"
    )
    
    var showDatePicker by remember { mutableStateOf(false) }
    var showCategoryDropdown by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Expense") },
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
                label = { Text("Expense Title *") },
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
            
            // Expense date picker
            OutlinedTextField(
                value = java.text.SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(expenseDate),
                onValueChange = { },
                readOnly = true,
                label = { Text("Date *") },
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
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = {
                    if (title.isNotEmpty() && amount.isNotEmpty() && category.isNotEmpty()) {
                        val expense = Expense(
                            title = title,
                            amount = amount.toDoubleOrNull() ?: 0.0,
                            category = category,
                            date = expenseDate,
                            description = description.takeIf { it.isNotEmpty() }
                        )
                        expenseViewModel.addExpense(expense)
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = title.isNotEmpty() && amount.isNotEmpty() && category.isNotEmpty()
            ) {
                Text("Add Expense")
            }
        }
    }
    
    // Date picker dialog
    if (showDatePicker) {
        AlertDialog(
            onDismissRequest = { showDatePicker = false },
            title = { Text("Select Date") },
            text = { Text("Date picker would be implemented here") },
            confirmButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("OK")
                }
            }
        )
    }
}