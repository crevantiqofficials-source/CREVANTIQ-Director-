package com.crevantiq.director

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DirectorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WelcomeScreen()
                }
            }
        }
    }
}

@Composable
fun WelcomeScreen() {
    val darkBg = Color(0xFF0D1117)
    val accentStart = Color(0xFF6C63FF)
    val accentEnd = Color(0xFF3F8CFC)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBg),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "CREVANTIQ",
            fontSize = 42.sp,
            fontWeight = FontWeight.Bold,
            color = accentStart,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "DIRECTOR",
            fontSize = 36.sp,
            fontWeight = FontWeight.SemiBold,
            brush = Brush.horizontalGradient(listOf(accentStart, accentEnd)),
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Text(
            text = "Welcome",
            fontSize = 20.sp,
            color = Color(0xFF8B949E),
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "v0.7.6",
            fontSize = 14.sp,
            color = Color(0xFF6E7681)
        )
    }
}

@Composable
fun DirectorTheme(content: @Composable () -> Unit) {
    val darkColorScheme = androidx.compose.material3.darkColorScheme(
        primary = Color(0xFF6C63FF),
        surface = Color(0xFF0D1117),
        background = Color(0xFF0D1117)
    )
    MaterialTheme(colorScheme = darkColorScheme) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    DirectorTheme {
        WelcomeScreen()
    }
}
