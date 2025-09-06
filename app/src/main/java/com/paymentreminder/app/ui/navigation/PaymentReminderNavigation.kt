package com.paymentreminder.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.paymentreminder.app.ui.screens.HomeScreen
import com.paymentreminder.app.ui.screens.PaymentsScreen
import com.paymentreminder.app.ui.screens.ExpensesScreen
import com.paymentreminder.app.ui.screens.CameraScreen
import com.paymentreminder.app.ui.screens.AddPaymentScreen
import com.paymentreminder.app.ui.screens.AddExpenseScreen

@Composable
fun PaymentReminderNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(navController = navController)
        }
        composable("payments") {
            PaymentsScreen(navController = navController)
        }
        composable("expenses") {
            ExpensesScreen(navController = navController)
        }
        composable("camera") {
            CameraScreen(navController = navController)
        }
        composable("add_payment") {
            AddPaymentScreen(navController = navController)
        }
        composable("add_expense") {
            AddExpenseScreen(navController = navController)
        }
    }
}