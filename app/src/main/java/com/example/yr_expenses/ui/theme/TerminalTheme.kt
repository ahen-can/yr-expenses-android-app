/*package com.example.yr_expenses.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// PURE TERMINAL THEME COLORS
val TerminalGreen = Color(0xFF00FF00)
private val TerminalBlack = Color(0xFF000000)

private val TerminalColorScheme = darkColorScheme(
    primary = TerminalGreen,
    onPrimary = TerminalBlack,
    background = TerminalBlack,
    surface = TerminalBlack,
    onBackground = TerminalGreen,
    onSurface = TerminalGreen,
    outline = TerminalGreen
)

@Composable
fun TerminalTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = TerminalColorScheme,
        typography = Typography(),
        content = content
    )
}
*/


package com.example.yr_expenses.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val TerminalGreen = Color(0xFF00FF00)

@Composable
fun TerminalTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = androidx.compose.material3.darkColorScheme(
            primary = TerminalGreen,
            onPrimary = Color.Black,
            secondary = TerminalGreen,
            onSecondary = Color.Black,
            background = Color.Black,
            onBackground = TerminalGreen,
            surface = Color.Black,
            onSurface = TerminalGreen,
            error = Color.Red,
            onError = Color.Black
        ),
        content = content
    )
}
