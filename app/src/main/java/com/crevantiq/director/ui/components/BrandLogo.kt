package com.crevantiq.director.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crevantiq.director.R
import com.crevantiq.director.ui.theme.*

@Composable
fun BrandHeader(
    modifier: Modifier = Modifier,
    compact: Boolean = false
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .size(if (compact) 36.dp else 48.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, CrevantiqBorder, RoundedCornerShape(12.dp))
                .background(CrevantiqPanel)
        ) {
            Image(
                painter = painterResource(id = R.drawable.crevantiq_brand),
                contentDescription = "CREVANTIQ Logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize().padding(3.dp)
            )
        }

        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "CREVANTIQ",
                    color = CrevantiqTextPrimary,
                    fontSize = if (compact) 18.sp else 22.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 2.sp
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    text = "DIRECTOR",
                    color = CrevantiqCyan,
                    fontSize = if (compact) 11.sp else 13.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
            }
            if (!compact) {
                Text(
                    text = "AI CREATOR OPERATING SYSTEM",
                    color = CrevantiqTextSecondary,
                    fontSize = 10.sp,
                    letterSpacing = 1.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
