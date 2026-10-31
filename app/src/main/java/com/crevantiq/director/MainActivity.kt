package com.crevantiq.director

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Bg = Color(0xFF090E17)
private val Panel = Color(0xFF121C2B)
private val Cyan = Color(0xFF54D6E8)
private val Muted = Color(0xFF9AAABD)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { DirectorTheme { CreatorWorkspace() } }
    }
}

@Composable
private fun DirectorTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = darkColorScheme(
        primary = Cyan, secondary = Color(0xFF8B83FF),
        background = Bg, surface = Panel,
        onBackground = Color.White, onSurface = Color.White
    ), content = content)
}

@Composable
private fun CreatorWorkspace() {
    var selected by remember { mutableStateOf("Home") }
    var idea by remember { mutableStateOf("") }
    var savedIdeas by remember { mutableStateOf(listOf("বাংলা Baul short film", "Behind-the-scenes creator story")) }
    val tabs = listOf("Home", "Tools", "Create", "Library", "Profile")
    Scaffold(containerColor = Bg, bottomBar = {
        Row(Modifier.fillMaxWidth().background(Panel).navigationBarsPadding().padding(vertical = 8.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            tabs.forEach { tab ->
                Column(Modifier.weight(1f).clickable { selected = tab }.padding(4.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(if (selected == tab) "●" else "○", color = if (selected == tab) Cyan else Muted, fontSize = 16.sp)
                    Text(tab, color = if (selected == tab) Cyan else Muted, fontSize = 10.sp, fontWeight = if (selected == tab) FontWeight.Bold else FontWeight.Normal)
                }
            }
        }
    }) { padding ->
        LazyColumn(Modifier.fillMaxSize().padding(padding).padding(horizontal = 18.dp), verticalArrangement = Arrangement.spacedBy(14.dp), contentPadding = PaddingValues(top = 22.dp, bottom = 24.dp)) {
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("CREVANTIQ", color = Color.White, fontSize = 23.sp, fontWeight = FontWeight.Black, letterSpacing = 2.sp)
                        Text("DIRECTOR  •  CREATOR OS", color = Cyan, fontSize = 10.sp, letterSpacing = 1.5.sp)
                    }
                    Text("v0.7.6", color = Muted, fontSize = 11.sp)
                }
            }
            item { Text(when(selected) { "Home" -> "Good day, Creator"; "Tools" -> "Creator Tools"; "Create" -> "Create Workspace"; "Library" -> "Your Library"; else -> "Creator Profile" }, color = Color.White, fontSize = 25.sp, fontWeight = FontWeight.Bold) }
            when (selected) {
                "Home" -> {
                    item { InfoCard("YOUR DIRECTOR BRIEF", "Your workspace is ready. Plan content, organize assets and prepare platform-specific drafts. No account is connected yet.") }
                    item { Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) { MetricCard("Projects", savedIdeas.size.toString(), Modifier.weight(1f)); MetricCard("Platforms", "0/4", Modifier.weight(1f)); MetricCard("AI Credits", "Not set", Modifier.weight(1f)) } }
                    item { InfoCard("NEXT BEST ACTION", "Create one content concept, then adapt its hook and format for each platform.") }
                    item { Text("QUICK ACCESS", color = Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold) }
                    item { Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) { ActionButton("New idea", Modifier.weight(1f)) { selected = "Create" }; ActionButton("Open library", Modifier.weight(1f)) { selected = "Library" } } }
                    item { InfoCard("CONNECTION STATUS", "YouTube • Facebook • Instagram • TikTok\nNot connected — official OAuth setup will be added after backend configuration.") }
                }
                "Tools" -> {
                    item { InfoCard("IDEA DIRECTOR", "Turn a rough concept into a structured content brief. AI generation is not connected in this build.") }
                    item { InfoCard("SCRIPT & HOOK", "Prepare a hook, outline, call to action and platform-specific draft.") }
                    item { InfoCard("MEDIA & SUBTITLES", "Organize media assets and subtitle work. Audio transcription is not enabled yet.") }
                    item { InfoCard("THUMBNAIL WORKSPACE", "Plan thumbnail concepts and aspect ratios. Image generation is not connected.") }
                    item { InfoCard("PUBLISHING", "Draft review and approval workspace. Publishing remains disabled until official platform connections are implemented.") }
                }
                "Create" -> {
                    item { Text("CONTENT CONCEPT", color = Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold) }
                    item { OutlinedTextField(value = idea, onValueChange = { idea = it }, modifier = Modifier.fillMaxWidth(), minLines = 3, label = { Text("What do you want to create?") }, placeholder = { Text("Write your idea…") }) }
                    item { Button(onClick = { if (idea.isNotBlank()) { savedIdeas = listOf(idea.trim()) + savedIdeas; idea = "" } }, modifier = Modifier.fillMaxWidth(), enabled = idea.isNotBlank()) { Text("Save content idea") } }
                    item { InfoCard("WORKFLOW", "Idea → Brief → Script → Media → Review → Platform-ready draft") }
                    item { InfoCard("IMPORTANT", "This build saves ideas locally in the current app session only. Cloud sync and AI generation are not connected.") }
                }
                "Library" -> {
                    item { InfoCard("PROJECTS & IDEAS", "Saved in this session") }
                    items(savedIdeas) { saved -> InfoCard("CONTENT IDEA", saved) }
                    item { Text("Media files, subtitle tracks and exports will appear here when local storage workflows are implemented.", color = Muted, fontSize = 13.sp) }
                }
                else -> {
                    item { InfoCard("ACCOUNT", "Guest workspace • Authentication not connected") }
                    item { InfoCard("CREATOR MODE", "MANUAL active\nCOPILOT and AUTO are unavailable until explicit consent and backend controls are implemented.") }
                    item { InfoCard("PLATFORM CONNECTIONS", "No connected accounts") }
                    item { InfoCard("SUBSCRIPTION & CREDITS", "Plan and credit balance are not configured. No payment processing is enabled.") }
                    item { InfoCard("DEVICES & SECURITY", "Device registration and account security setup are pending.") }
                }
            }
        }
    }
}

@Composable
private fun InfoCard(title: String, body: String) {
    Card(colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(title, color = Cyan, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
            Text(body, color = Color.White, fontSize = 14.sp, lineHeight = 20.sp)
        }
    }
}

@Composable
private fun MetricCard(title: String, value: String, modifier: Modifier = Modifier) {
    Card(modifier, colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(14.dp)) {
        Column(Modifier.padding(12.dp)) { Text(title, color = Muted, fontSize = 10.sp); Spacer(Modifier.height(6.dp)); Text(value, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold) }
    }
}

@Composable
private fun ActionButton(label: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    OutlinedButton(onClick = onClick, modifier = modifier) { Text(label, fontSize = 12.sp) }
}
