package com.example.yr_expenses.data

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

object AnalyticsEngine {

    // ─────────────────────────────────────────────
    // MONTH KEY
    // Example: "Apr 2026"
    // ─────────────────────────────────────────────

    private val monthFormatter =
        SimpleDateFormat("MMM yyyy", Locale.getDefault())

    fun getMonthKey(timestamp: Long): String {
        return monthFormatter.format(Date(timestamp))
    }

    // ─────────────────────────────────────────────
    // FILTER TXNS
    // ─────────────────────────────────────────────

    private fun filterTransactions(
        txns: List<Transaction>,
        selectedMonth: String
    ): List<Transaction> {

        if (selectedMonth == "Till Now")
            return txns

        return txns.filter {
            getMonthKey(it.timestamp) == selectedMonth
        }
    }

    // ─────────────────────────────────────────────
    // TOTAL SPEND
    // ─────────────────────────────────────────────

    fun totalSpend(
        txns: List<Transaction>,
        selectedMonth: String
    ): Int {

        return filterTransactions(txns, selectedMonth)
            .sumOf { it.amount.toDouble() }
            .roundToInt()
    }

    // ─────────────────────────────────────────────
    // TRANSACTION COUNT
    // ─────────────────────────────────────────────

    fun transactionCount(
        txns: List<Transaction>,
        selectedMonth: String
    ): Int {

        return filterTransactions(txns, selectedMonth).size
    }

    // ─────────────────────────────────────────────
    // TOP CATEGORY
    // ─────────────────────────────────────────────

    fun topCategory(
        txns: List<Transaction>,
        selectedMonth: String
    ): Pair<String, Int> {

        val filtered = filterTransactions(txns, selectedMonth)

        if (filtered.isEmpty())
            return "None" to 0

        val grouped = filtered.groupBy { it.category }

        val top = grouped.maxByOrNull {
            it.value.sumOf { txn -> txn.amount.toDouble() }
        }

        val total = top?.value
            ?.sumOf { it.amount.toDouble() }
            ?.roundToInt()
            ?: 0

        return (top?.key ?: "None") to total
    }

    // ─────────────────────────────────────────────
    // TOP MERCHANT
    // ─────────────────────────────────────────────

    fun topMerchant(
        txns: List<Transaction>,
        selectedMonth: String
    ): Pair<String, Int> {

        val filtered = filterTransactions(txns, selectedMonth)

        if (filtered.isEmpty())
            return "None" to 0

        val grouped = filtered.groupBy { it.merchant }

        val top = grouped.maxByOrNull {
            it.value.sumOf { txn -> txn.amount.toDouble() }
        }

        val total = top?.value
            ?.sumOf { it.amount.toDouble() }
            ?.roundToInt()
            ?: 0

        return (top?.key ?: "None") to total
    }

    // ─────────────────────────────────────────────
    // CATEGORY BREAKDOWN
    // ─────────────────────────────────────────────

    fun categoryBreakdown(
        txns: List<Transaction>,
        selectedMonth: String
    ): List<Pair<String, Int>> {

        val filtered = filterTransactions(txns, selectedMonth)

        return filtered
            .groupBy { it.category }
            .mapValues {
                it.value.sumOf { txn -> txn.amount.toDouble() }
                    .roundToInt()
            }
            .toList()
            .sortedByDescending { it.second }
    }

    // ─────────────────────────────────────────────
    // MONTHLY TOTALS
    // ─────────────────────────────────────────────

    fun monthlyTotals(
        txns: List<Transaction>
    ): List<Pair<String, Int>> {

        return txns
            .groupBy {
                getMonthKey(it.timestamp)
            }
            .mapValues {
                it.value.sumOf { txn -> txn.amount.toDouble() }
                    .roundToInt()
            }
            .toList()
            .sortedBy { pair ->

                val parser = SimpleDateFormat("MMM yyyy", Locale.getDefault())

                parser.parse(pair.first)?.time ?: 0L
            }
    }

    // ─────────────────────────────────────────────
    // AVAILABLE MONTHS
    // ─────────────────────────────────────────────

    fun availableMonths(txns: List<Transaction>): List<String> {

        val months = txns.map {
            getMonthKey(it.timestamp)
        }.distinct()

        return listOf("Till Now") + months.sortedDescending()
    }
}