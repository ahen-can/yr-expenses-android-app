package com.example.yr_expenses.ui.theme

import androidx.compose.ui.graphics.Color
import kotlin.math.abs

object CategoryColors {

    private val neonPalette = listOf(

        Color(0xFF00FF00), // neon green
        Color(0xFF00FFFF), // cyan
        Color(0xFFFF00FF), // magenta
        Color(0xFFFFFF00), // yellow
        Color(0xFFFF8800), // orange
        Color(0xFF39FF14), // lime
        Color(0xFFFF1493), // pink
        Color(0xFF00BFFF)  // electric blue

    )

    fun getColor(category: String): Color {

        val index = abs(category.hashCode()) % neonPalette.size

        return neonPalette[index]
    }
}