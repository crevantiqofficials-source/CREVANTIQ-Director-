package com.crevantiq.director

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Bg = Color(0xFF07111F)
private val Panel = Color(0xFF101D2D)
private val Surface = Color(0xFF14263A)
private val Border = Color(0xFF243A53)
private val Cyan = Color(0xFF46D7E8)
private val Violet = Color(0xFF9B8CFF)
private val Green = Color(0xFF69D9B0)
private val MainText = Color(0xFFF2F7FF)
private val Muted = Color(0xFF9AAFC5)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MaterialTheme(colorScheme = darkColorScheme(primary=Cyan, secondary=Violet, background=Bg, surface=Panel, onSurface=MainText)) { Workspace() } }
    }
}

private enum class Page(val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    HOME("Home", Icons.Default.Dashboard), TOOLS("Tools", Icons.Default.AutoAwesome),
    CREATE("Create", Icons.Default.AddCircle), LIBRARY("Library", Icons.Default.FolderOpen),
    PROFILE("Profile", Icons.Default.AccountCircle)
}

@Composable
private fun Workspace() {
    var page by remember { mutableStateOf(Page.HOME) }
    var mode by remember { mutableStateOf("COPILOT") }
    var idea by remember { mutableStateOf("") }
    var hook by remember { mutableStateOf("") }
    var search by remember { mutableStateOf("") }
    var ideas by remember { mutableStateOf(listOf("বাংলা Baul short film", "Behind-the-scenes creator story")) }

    Scaffold(
        containerColor=Bg,
        topBar={
            Row(Modifier.fillMaxWidth().background(Bg).statusBarsPadding().padding(horizontal=18.dp, vertical=12.dp), verticalAlignment=Alignment.CenterVertically) {
                Box(Modifier.size(42.dp).clip(RoundedCornerShape(13.dp)).background(Brush.linearGradient(listOf(Cyan,Violet))), contentAlignment=Alignment.Center) {
                    Text("C", color=Bg, fontSize=25.sp, fontWeight=FontWeight.Black)
                }
                Spacer(Modifier.width(10.dp))
                Column(Modifier.weight(1f)) {
                    Text("CREVANTIQ", color=MainText, fontSize=19.sp, fontWeight=FontWeight.Black, letterSpacing=1.7.sp)
                    Text("DIRECTOR  /  CREATOR OS", color=Cyan, fontSize=9.sp, fontWeight=FontWeight.Bold, letterSpacing=1.2.sp)
                }
                Column(horizontalAlignment=Alignment.End) {
                    Text("v0.7.6", color=Muted, fontSize=10.sp)
                    Text("WORKSPACE", color=Green, fontSize=8.sp, fontWeight=FontWeight.Bold)
                }
            }
        },
        bottomBar={
            NavigationBar(containerColor=Panel, tonalElevation=0.dp) {
                Page.values().forEach { item ->
                    NavigationBarItem(selected=page==item, onClick={page=item},
                        icon={Icon(item.icon, contentDescription=item.label, modifier=Modifier.size(21.dp))},
                        label={Text(item.label, fontSize=10.sp)},
                        colors=NavigationBarItemDefaults.colors(selectedIconColor=Bg, selectedTextColor=Cyan, indicatorColor=Cyan, unselectedIconColor=Muted, unselectedTextColor=Muted))
                }
            }
        }
    ) { pad ->
        when(page) {
            Page.HOME -> Home(Modifier.padding(pad), mode, {mode=it}, ideas.size, {page=Page.CREATE}, {page=Page.LIBRARY})
            Page.TOOLS -> Tools(Modifier.padding(pad))
            Page.CREATE -> Create(Modifier.padding(pad), idea, {idea=it}, hook, {hook=it}) {
                if(idea.isNotBlank()) { ideas=listOf(idea.trim())+ideas; idea=""; hook=""; page=Page.LIBRARY }
            }
            Page.LIBRARY -> Library(Modifier.padding(pad), ideas, search, {search=it})
            Page.PROFILE -> Profile(Modifier.padding(pad))
        }
    }
}

@Composable
private fun Home(modifier:Modifier, mode:String, setMode:(String)->Unit, count:Int, create:()->Unit, library:()->Unit) {
    LazyColumn(modifier.fillMaxSize().padding(horizontal=18.dp), contentPadding=PaddingValues(vertical=16.dp), verticalArrangement=Arrangement.spacedBy(14.dp)) {
        item {
            Text("Good day, Creator", color=MainText, fontSize=25.sp, fontWeight=FontWeight.Bold)
            Text("Your creative workflow, directed from one place.", color=Muted, fontSize=13.sp)
        }
        item {
            Row(Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp)).background(Panel).border(1.dp,Border,RoundedCornerShape(14.dp)).padding(4.dp)) {
                listOf("MANUAL","COPILOT","AUTO").forEach { m ->
                    Box(Modifier.weight(1f).clip(RoundedCornerShape(10.dp)).background(if(mode==m) Cyan.copy(alpha=.15f) else Color.Transparent)
                        .clickable(enabled=m!="AUTO"){setMode(m)}.padding(vertical=11.dp), contentAlignment=Alignment.Center) {
                        Text(m, color=if(mode==m) Cyan else Muted, fontSize=10.sp, fontWeight=FontWeight.Bold)
                    }
                }
            }
            Text("AUTO remains locked until explicit creator consent.", color=Muted, fontSize=10.sp, modifier=Modifier.padding(start=4.dp,top=4.dp))
        }
        item {
            Column(Modifier.fillMaxWidth().clip(RoundedCornerShape(19.dp)).background(Brush.linearGradient(listOf(Color(0xFF15334A),Color(0xFF24234B)))).padding(18.dp), verticalArrangement=Arrangement.spacedBy(9.dp)) {
                Row(verticalAlignment=Alignment.CenterVertically) {
                    Text("TODAY'S CREATOR BRIEF", color=Cyan, fontSize=10.sp, fontWeight=FontWeight.Bold, letterSpacing=1.sp)
                    Spacer(Modifier.weight(1f)); Pill("READY",Green)
                }
                Text("Turn one strong idea into a complete content workflow.", color=MainText, fontSize=19.sp, fontWeight=FontWeight.Bold, lineHeight=25.sp)
                Text("Plan  •  Create  •  Organize  •  Review", color=Muted, fontSize=12.sp)
            }
        }
        item {
            Row(horizontalArrangement=Arrangement.spacedBy(9.dp)) {
                Metric("PROJECTS",count.toString(),"In workspace",Modifier.weight(1f))
                Metric("PLATFORMS","0 / 4","Not connected",Modifier.weight(1f))
                Metric("AI CREDITS","—","Not configured",Modifier.weight(1f))
            }
        }
        item {
            CardPanel {
                Heading("NEXT BEST ACTION","01")
                Text("Build your next content concept", color=MainText, fontSize=17.sp, fontWeight=FontWeight.Bold)
                Text("Start with a core idea, then prepare its hook, script and platform-ready variations.", color=Muted, fontSize=12.sp, lineHeight=18.sp)
                MainButton("START CREATING",create)
            }
        }
        item {
            CardPanel {
                Heading("QUICK ACCESS","WORKSPACE")
                Row(horizontalArrangement=Arrangement.spacedBy(9.dp)) {
                    OutlinedButton(onClick=create, modifier=Modifier.weight(1f), shape=RoundedCornerShape(11.dp)) { Icon(Icons.Default.Edit,null,tint=Cyan); Spacer(Modifier.width(5.dp)); Text("New concept",fontSize=11.sp) }
                    OutlinedButton(onClick=library, modifier=Modifier.weight(1f), shape=RoundedCornerShape(11.dp)) { Icon(Icons.Default.Folder,null,tint=Cyan); Spacer(Modifier.width(5.dp)); Text("Open library",fontSize=11.sp) }
                }
            }
        }
        item {
            CardPanel {
                Heading("PLATFORM CONNECTIONS","0 / 4")
                listOf("YouTube","Facebook","Instagram","TikTok").forEach { name ->
                    Row(Modifier.fillMaxWidth().padding(vertical=7.dp), verticalAlignment=Alignment.CenterVertically) {
                        Box(Modifier.size(7.dp).clip(CircleShape).background(Muted))
                        Spacer(Modifier.width(10.dp)); Text(name,color=MainText,fontSize=13.sp,modifier=Modifier.weight(1f))
                        Text("Not connected",color=Muted,fontSize=11.sp)
                    }
                    HorizontalDivider(color=Border.copy(alpha=.6f))
                }
            }
        }
    }
}

@Composable
private fun Tools(modifier:Modifier) {
    val data=listOf(
        Triple("Idea Director","Turn raw ideas into structured creative briefs.",Icons.Default.Lightbulb),
        Triple("Script & Hook","Create opening hooks, story beats and calls to action.",Icons.Default.Article),
        Triple("Media & Subtitles","Organize media, captions and subtitle workflows.",Icons.Default.Subtitles),
        Triple("Thumbnail Studio","Plan visual concepts for 16:9 and 9:16.",Icons.Default.Image),
        Triple("Platform Optimizer","Prepare drafts for each social platform.",Icons.Default.Tune),
        Triple("Publishing Desk","Review and approve content before publishing.",Icons.Default.Publish)
    )
    LazyColumn(modifier.fillMaxSize().padding(horizontal=18.dp),contentPadding=PaddingValues(vertical=18.dp),verticalArrangement=Arrangement.spacedBy(11.dp)) {
        item { Text("Creator Tools",color=MainText,fontSize=25.sp,fontWeight=FontWeight.Bold); Text("Your production pipeline in one workspace.",color=Muted,fontSize=13.sp) }
        items(data) { d -> CardPanel {
            Row(verticalAlignment=Alignment.CenterVertically) {
                Box(Modifier.size(43.dp).clip(RoundedCornerShape(12.dp)).background(Cyan.copy(alpha=.12f)),contentAlignment=Alignment.Center) { Icon(d.third,null,tint=Cyan) }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) { Text(d.first,color=MainText,fontSize=14.sp,fontWeight=FontWeight.Bold); Text(d.second,color=Muted,fontSize=11.sp,lineHeight=16.sp) }
                Icon(Icons.Default.ChevronRight,null,tint=Muted)
            }
        } }
        item { Note("AI generation, transcription and publishing are not connected in this build. These screens are currently workspace UI.") }
    }
}

@Composable
private fun Create(modifier:Modifier, idea:String, setIdea:(String)->Unit, hook:String, setHook:(String)->Unit, save:()->Unit) {
    LazyColumn(modifier.fillMaxSize().padding(horizontal=18.dp),contentPadding=PaddingValues(vertical=18.dp),verticalArrangement=Arrangement.spacedBy(13.dp)) {
        item { Text("Create Workspace",color=MainText,fontSize=25.sp,fontWeight=FontWeight.Bold); Text("Capture the idea. Shape the story. Prepare the draft.",color=Muted,fontSize=13.sp) }
        item {
            CardPanel {
                Heading("01  /  CORE CONCEPT","REQUIRED")
                OutlinedTextField(value=idea,onValueChange=setIdea,modifier=Modifier.fillMaxWidth(),label={Text("What do you want to create?")},placeholder={Text("Describe your content idea…")},minLines=3,shape=RoundedCornerShape(13.dp),colors=fieldColors())
                Heading("02  /  OPENING HOOK","OPTIONAL")
                OutlinedTextField(value=hook,onValueChange=setHook,modifier=Modifier.fillMaxWidth(),label={Text("First 3 seconds")},placeholder={Text("Write your opening line…")},minLines=2,shape=RoundedCornerShape(13.dp),colors=fieldColors())
                MainButton("SAVE CONTENT CONCEPT",save,idea.isNotBlank())
            }
        }
        item {
            CardPanel {
                Heading("PRODUCTION FLOW","6 STEPS")
                listOf("Idea","Creative brief","Script & hook","Media & subtitles","Review & approval","Platform-ready draft").forEachIndexed { i,label ->
                    Row(verticalAlignment=Alignment.CenterVertically,modifier=Modifier.padding(vertical=4.dp)) {
                        Pill("%02d".format(i+1),Cyan); Spacer(Modifier.width(10.dp)); Text(label,color=MainText,fontSize=13.sp)
                    }
                }
            }
        }
        item { Note("Concepts are currently held in app memory only. Cloud sync and AI generation will be connected after backend integration.") }
    }
}

@Composable
private fun Library(modifier:Modifier, ideas:List<String>, search:String, setSearch:(String)->Unit) {
    val matches=ideas.filter{it.contains(search,ignoreCase=true)}
    LazyColumn(modifier.fillMaxSize().padding(horizontal=18.dp),contentPadding=PaddingValues(vertical=18.dp),verticalArrangement=Arrangement.spacedBy(11.dp)) {
        item { Text("Your Library",color=MainText,fontSize=25.sp,fontWeight=FontWeight.Bold); Text("Projects and concepts saved in this session.",color=Muted,fontSize=13.sp) }
        item { OutlinedTextField(value=search,onValueChange=setSearch,modifier=Modifier.fillMaxWidth(),singleLine=true,placeholder={Text("Search your library")},leadingIcon={Icon(Icons.Default.Search,null)},shape=RoundedCornerShape(13.dp),colors=fieldColors()) }
        item { Heading("CONTENT CONCEPTS",matches.size.toString()+" ITEMS") }
        if(matches.isEmpty()) item { Note("No matching concepts. Create a new idea or change your search.") }
        items(matches) { idea -> CardPanel {
            Row(verticalAlignment=Alignment.CenterVertically) {
                Icon(Icons.Default.Description,null,tint=Violet,modifier=Modifier.size(25.dp))
                Spacer(Modifier.width(12.dp))
                Column { Text(idea,color=MainText,fontSize=14.sp,fontWeight=FontWeight.SemiBold); Text("CONTENT CONCEPT  •  LOCAL SESSION",color=Muted,fontSize=9.sp) }
            }
        } }
        item { Note("Media files, subtitle tracks and exports will appear here after local storage workflows are implemented.") }
    }
}

@Composable
private fun Profile(modifier:Modifier) {
    LazyColumn(modifier.fillMaxSize().padding(horizontal=18.dp),contentPadding=PaddingValues(vertical=18.dp),verticalArrangement=Arrangement.spacedBy(11.dp)) {
        item { Text("Creator Profile",color=MainText,fontSize=25.sp,fontWeight=FontWeight.Bold); Text("Account, preferences and workspace controls.",color=Muted,fontSize=13.sp) }
        item { CardPanel {
            Row(verticalAlignment=Alignment.CenterVertically) {
                Box(Modifier.size(50.dp).clip(CircleShape).background(Brush.linearGradient(listOf(Cyan,Violet))),contentAlignment=Alignment.Center) { Text("G",color=Bg,fontSize=22.sp,fontWeight=FontWeight.Black) }
                Spacer(Modifier.width(12.dp)); Column(Modifier.weight(1f)) { Text("Guest Creator",color=MainText,fontSize=15.sp,fontWeight=FontWeight.Bold); Text("Local preview workspace",color=Muted,fontSize=11.sp) }
                Pill("GUEST",Muted)
            }
        } }
        item { ProfileItem("Creator mode","MANUAL",Icons.Default.Tune) }
        item { ProfileItem("Connected platforms","0 of 4 connected",Icons.Default.Share) }
        item { ProfileItem("Subscription","Plan not configured",Icons.Default.WorkspacePremium) }
        item { ProfileItem("AI credits","Not configured",Icons.Default.Token) }
        item { ProfileItem("Devices & security","Setup pending",Icons.Default.Security) }
        item { ProfileItem("Language & region","Bangla / English  •  Asia/Dhaka",Icons.Default.Language) }
        item { Note("Authentication, device registration, subscriptions and platform OAuth are not active in this UI preview.") }
    }
}

@Composable
private fun ProfileItem(title:String,detail:String,icon:androidx.compose.ui.graphics.vector.ImageVector) {
    CardPanel { Row(verticalAlignment=Alignment.CenterVertically) {
        Icon(icon,null,tint=Cyan); Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) { Text(title,color=MainText,fontSize=13.sp,fontWeight=FontWeight.SemiBold); Text(detail,color=Muted,fontSize=11.sp) }
        Icon(Icons.Default.ChevronRight,null,tint=Muted)
    } }
}

@Composable
private fun Metric(title:String,value:String,detail:String,modifier:Modifier) {
    Column(modifier.clip(RoundedCornerShape(14.dp)).background(Panel).border(1.dp,Border,RoundedCornerShape(14.dp)).padding(10.dp)) {
        Text(title,color=Muted,fontSize=8.sp,fontWeight=FontWeight.Bold)
        Spacer(Modifier.height(7.dp)); Text(value,color=MainText,fontSize=18.sp,fontWeight=FontWeight.Bold)
        Text(detail,color=Muted,fontSize=9.sp,maxLines=1,overflow=TextOverflow.Ellipsis)
    }
}

@Composable
private fun CardPanel(content:@Composable ColumnScope.()->Unit) {
    Column(Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(Panel).border(1.dp,Border,RoundedCornerShape(16.dp)).padding(14.dp),verticalArrangement=Arrangement.spacedBy(9.dp),content=content)
}
@Composable private fun Heading(title:String,meta:String) {
    Row(Modifier.fillMaxWidth(),verticalAlignment=Alignment.CenterVertically) { Text(title,color=Cyan,fontSize=10.sp,fontWeight=FontWeight.Bold,letterSpacing=.7.sp,modifier=Modifier.weight(1f)); Text(meta,color=Muted,fontSize=9.sp,fontWeight=FontWeight.Bold) }
}
@Composable private fun Pill(text:String,color:Color) {
    Box(Modifier.clip(RoundedCornerShape(20.dp)).background(color.copy(alpha=.13f)).padding(horizontal=8.dp,vertical=5.dp)) { Text(text,color=color,fontSize=8.sp,fontWeight=FontWeight.Bold) }
}
@Composable private fun MainButton(label:String,onClick:()->Unit,enabled:Boolean=true) {
    Button(onClick=onClick,enabled=enabled,modifier=Modifier.fillMaxWidth().height(46.dp),shape=RoundedCornerShape(11.dp),colors=ButtonDefaults.buttonColors(containerColor=Cyan,contentColor=Bg,disabledContainerColor=Surface,disabledContentColor=Muted)) { Text(label,fontSize=10.sp,fontWeight=FontWeight.Black,letterSpacing=.6.sp) }
}
@Composable private fun Note(text:String) {
    Row(Modifier.fillMaxWidth().clip(RoundedCornerShape(11.dp)).background(Surface.copy(alpha=.7f)).padding(11.dp),verticalAlignment=Alignment.Top) { Icon(Icons.Default.Info,null,tint=Cyan,modifier=Modifier.size(16.dp)); Spacer(Modifier.width(8.dp)); Text(text,color=Muted,fontSize=11.sp,lineHeight=16.sp) }
}
@Composable private fun fieldColors()=OutlinedTextFieldDefaults.colors(focusedBorderColor=Cyan,unfocusedBorderColor=Border,focusedTextColor=MainText,unfocusedTextColor=MainText,focusedLabelColor=Cyan,unfocusedLabelColor=Muted,cursorColor=Cyan,focusedContainerColor=Bg,unfocusedContainerColor=Bg)
