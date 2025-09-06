package com.paymentreminder.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.paymentreminder.app.data.entity.Payment
import com.paymentreminder.app.ui.components.PaymentCard
import com.paymentreminder.app.ui.components.ExpenseSummaryCard
import com.paymentreminder.app.ui.viewmodel.ExpenseViewModel
import com.paymentreminder.app.ui.viewmodel.PaymentViewModel
import java.text.NumberFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    paymentViewModel: PaymentViewModel = hiltViewModel(),
    expenseViewModel: ExpenseViewModel = hiltViewModel()
) {
    val upcomingPayments by paymentViewModel.upcomingPayments.collectAsState()
    val monthlyExpense by expenseViewModel.totalMonthlyExpense.collectAsState()
    val categoryTotals by expenseViewModel.categoryTotals.collectAsState()
    
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Payment Reminder") },
                actions = {
                    IconButton(onClick = { navController.navigate("camera") }) {
                        Icon(Icons.Default.CameraAlt, contentDescription = "Scan Receipt")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("add_payment") }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Payment")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Monthly Expenses",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = currencyFormat.format(monthlyExpense),
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Card(
                        modifier = Modifier.weight(1f),
                        onClick = { navController.navigate("payments") }
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Default.Payment,
                                contentDescription = "Payments",
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Payments")
                        }
                    }
                    
                    Card(
                        modifier = Modifier.weight(1f),
                        onClick = { navController.navigate("expenses") }
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Default.Receipt,
                                contentDescription = "Expenses",
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Expenses")
                        }
                    }
                }
            }
            
            if (upcomingPayments.isNotEmpty()) {
                item {
                    Text(
                        text = "Upcoming Payments",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                items(upcomingPayments.take(3)) { payment ->
                    PaymentCard(
                        payment = payment,
                        onMarkAsPaid = { paymentViewModel.markAsPaid(payment.id) },
                        onEdit = { /* TODO: Navigate to edit screen */ }
                    )
                }
                
                if (upcomingPayments.size > 3) {
                    item {
                        TextButton(
                            onClick = { navController.navigate("payments") },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("View All Payments")
                        }
                    }
                }
            }
            
            if (categoryTotals.isNotEmpty()) {
                item {
                    Text(
                        text = "Expense Categories",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                items(categoryTotals.take(3)) { categoryTotal ->
                    ExpenseSummaryCard(
                        category = categoryTotal.category,
                        amount = categoryTotal.total,
                        currencyFormat = currencyFormat
                    )
                }
            }
        }
    }
}