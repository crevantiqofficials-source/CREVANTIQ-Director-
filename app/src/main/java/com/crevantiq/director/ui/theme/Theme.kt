package com.crevantiq.director.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val CrevantiqColorScheme = darkColorScheme(
    primary = CrevantiqCyan,
    onPrimary = CrevantiqBg,
    primaryContainer = CrevantiqSurface,
    onPrimaryContainer = CrevantiqTextPrimary,
    secondary = CrevantiqViolet,
    onSecondary = CrevantiqTextPrimary,
    secondaryContainer = CrevantiqPanel,
    onSecondaryContainer = CrevantiqTextPrimary,
    tertiary = CrevantiqEmerald,
    onTertiary = CrevantiqBg,
    background = CrevantiqBg,
    onBackground = CrevantiqTextPrimary,
    surface = CrevantiqPanel,
    onSurface = CrevantiqTextPrimary,
    surfaceVariant = CrevantiqSurface,
    onSurfaceVariant = CrevantiqTextSecondary,
    outline = CrevantiqBorder
)

@Composable
fun CREVANTIQTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = CrevantiqColorScheme,
        typography = Typography,
        content = content
    )
}
