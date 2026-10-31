package com.crevantiq.director.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crevantiq.director.ui.theme.*

@Composable
fun BrandHeader(modifier: Modifier = Modifier, compact: Boolean = false) {
 Row(modifier, verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
  Box(Modifier.size(if (compact) 36.dp else 48.dp).clip(RoundedCornerShape(12.dp)).border(1.dp, CrevantiqBorder, RoundedCornerShape(12.dp)).background(CrevantiqPanel), contentAlignment = Alignment.Center) {
   Text("C", color = CrevantiqCyan, fontSize = 25.sp, fontWeight = FontWeight.Black)
  }
  Column {
   Row(verticalAlignment = Alignment.CenterVertically) {
    Text("CREVANTIQ", color = CrevantiqTextPrimary, fontSize = if (compact) 18.sp else 22.sp, fontWeight = FontWeight.Black, letterSpacing = 2.sp)
    Spacer(Modifier.width(6.dp)); Text("DIRECTOR", color = CrevantiqCyan, fontSize = if (compact) 11.sp else 13.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
   }
   if (!compact) Text("AI CREATOR OPERATING SYSTEM", color = CrevantiqTextSecondary, fontSize = 10.sp, letterSpacing = 1.sp, fontWeight = FontWeight.Medium)
  }
 }
}
