package com.paymentreminder.app.camera

import java.text.NumberFormat
import java.util.*
import java.util.regex.Pattern
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReceiptParser @Inject constructor() {
    
    fun parseReceiptText(text: String): ReceiptData? {
        val lines = text.split("\n").map { it.trim() }
        
        var amount: Double? = null
        var merchant: String? = null
        var date: String? = null
        
        // Look for amount patterns
        val amountPatterns = listOf(
            Pattern.compile("\\$?\\d+\\.\\d{2}"),
            Pattern.compile("Total.*?\\$?\\d+\\.\\d{2}"),
            Pattern.compile("Amount.*?\\$?\\d+\\.\\d{2}")
        )
        
        for (line in lines) {
            for (pattern in amountPatterns) {
                val matcher = pattern.matcher(line)
                if (matcher.find()) {
                    val amountStr = matcher.group().replace("$", "").replace("Total", "").replace("Amount", "").trim()
                    try {
                        amount = amountStr.toDouble()
                        break
                    } catch (e: NumberFormatException) {
                        continue
                    }
                }
            }
        }
        
        // Look for merchant name (usually first non-empty line or contains common merchant keywords)
        val merchantKeywords = listOf("store", "shop", "restaurant", "cafe", "market", "pharmacy", "gas", "station")
        for (line in lines.take(5)) { // Check first 5 lines
            if (line.isNotEmpty() && !line.matches(Regex("\\d+.*")) && 
                merchantKeywords.any { line.lowercase().contains(it) }) {
                merchant = line
                break
            }
        }
        
        // Look for date patterns
        val datePatterns = listOf(
            Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{2,4}"),
            Pattern.compile("\\d{1,2}-\\d{1,2}-\\d{2,4}"),
            Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2}")
        )
        
        for (line in lines) {
            for (pattern in datePatterns) {
                val matcher = pattern.matcher(line)
                if (matcher.find()) {
                    date = matcher.group()
                    break
                }
            }
        }
        
        return if (amount != null) {
            ReceiptData(
                amount = amount,
                merchant = merchant ?: "Unknown Merchant",
                date = date ?: "",
                rawText = text
            )
        } else null
    }
}

data class ReceiptData(
    val amount: Double,
    val merchant: String,
    val date: String,
    val rawText: String
)