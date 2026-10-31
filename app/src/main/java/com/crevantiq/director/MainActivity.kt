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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Bg = Color(0xFF05101F)
private val Panel = Color(0xFF0B182A)
private val Surface = Color(0xFF0E2038)
private val Line = Color(0xFF1D395C)
private val Cyan = Color(0xFF14B8FF)
private val Violet = Color(0xFF8B5CF6)
private val Emerald = Color(0xFF6FF1BD)
private val Amber = Color(0xFFFFD17B)
private val Coral = Color(0xFFFF8B9C)
private val Ink = Color(0xFFEEF5FF)
private val Muted = Color(0xFF9CB0CF)
private val Quiet = Color(0xFF6F85A4)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = android.graphics.Color.rgb(5,16,31)
        window.navigationBarColor = android.graphics.Color.rgb(11,24,42)
        setContent {
            MaterialTheme(colorScheme = darkColorScheme(primary=Cyan, secondary=Violet, background=Bg, surface=Panel, onSurface=Ink)) {
                CreatorWorkspace()
            }
        }
    }
}

private enum class Screen(val title:String, val icon:ImageVector) {
    HOME("Home",Icons.Default.Dashboard),
    PROJECTS("Projects",Icons.Default.Folder),
    ASSETS("Assets",Icons.Default.PermMedia),
    MEDIA("Media / Subtitles",Icons.Default.Subtitles),
    PLATFORMS("Platforms",Icons.Default.Share),
    OPPORTUNITIES("Opportunities",Icons.Default.Lightbulb),
    BUILDER("Content Builder",Icons.Default.EditNote),
    VISUAL("Visual Studio",Icons.Default.Palette),
    AI("AI Tools",Icons.Default.AutoAwesome),
    PLAN("Plan / Credits",Icons.Default.WorkspacePremium),
    PROFILE("Profile",Icons.Default.AccountCircle)
}

private data class Concept(val title:String,val hook:String,val status:String="DRAFT")
private data class ProjectData(val name:String,val format:String,val platform:String)

@Composable
private fun CreatorWorkspace() {
    var screen by remember { mutableStateOf(Screen.HOME) }
    var mode by remember { mutableStateOf("COPILOT") }
    var search by remember { mutableStateOf("") }
    var idea by remember { mutableStateOf("") }
    var hook by remember { mutableStateOf("") }
    var projectName by remember { mutableStateOf("") }
    var concepts by remember { mutableStateOf(listOf(Concept("বাংলা Baul short film","একটি শক্তিশালী প্রথম দৃশ্য"),Concept("Behind-the-scenes creator story","")) ) }
    var projects by remember { mutableStateOf(listOf(ProjectData("Baul Frame Stories","Short video","YouTube"))) }
    var selectedTool by remember { mutableStateOf("Idea Director") }
    val primary = listOf(Screen.HOME,Screen.PROJECTS,Screen.BUILDER,Screen.AI,Screen.PROFILE)

    Scaffold(
        containerColor=Bg,
        topBar={
            Column(Modifier.fillMaxWidth().background(Bg).statusBarsPadding()) {
                Row(Modifier.fillMaxWidth().padding(horizontal=18.dp,vertical=12.dp),verticalAlignment=Alignment.CenterVertically) {
                    Box(Modifier.size(42.dp).clip(RoundedCornerShape(13.dp)).background(Brush.linearGradient(listOf(Cyan,Violet))),contentAlignment=Alignment.Center) {
                        Text("C",color=Bg,fontSize=25.sp,fontWeight=FontWeight.Black)
                    }
                    Spacer(Modifier.width(11.dp))
                    Column(Modifier.weight(1f)) {
                        Text("CREVANTIQ",color=Ink,fontSize=18.sp,fontWeight=FontWeight.Black,letterSpacing=1.6.sp)
                        Text("DIRECTOR  /  CREATOR OPERATING SYSTEM",color=Cyan,fontSize=8.sp,fontWeight=FontWeight.Bold,letterSpacing=.8.sp)
                    }
                    Column(horizontalAlignment=Alignment.End) {
                        Text("v0.7.6",color=Muted,fontSize=10.sp)
                        Row(verticalAlignment=Alignment.CenterVertically) { Box(Modifier.size(6.dp).clip(CircleShape).background(Emerald)); Spacer(Modifier.width(4.dp)); Text("LOCAL WORKSPACE",color=Emerald,fontSize=8.sp,fontWeight=FontWeight.Bold) }
                    }
                }
                HorizontalDivider(color=Line.copy(alpha=.65f))
                Row(Modifier.fillMaxWidth().padding(horizontal=14.dp,vertical=8.dp),horizontalArrangement=Arrangement.spacedBy(7.dp)) {
                    listOf(Screen.HOME,Screen.PROJECTS,Screen.ASSETS,Screen.MEDIA,Screen.PLATFORMS).forEach { s ->
                        FilterChip(selected=screen==s,onClick={screen=s},label={Text(s.title,fontSize=9.sp)},colors=FilterChipDefaults.filterChipColors(selectedContainerColor=Cyan.copy(alpha=.16f),selectedLabelColor=Cyan,containerColor=Panel,labelColor=Muted),border=FilterChipDefaults.filterChipBorder(enabled=true,selected=screen==s,borderColor=Line,selectedBorderColor=Cyan.copy(alpha=.5f)))
                    }
                }
            }
        },
        bottomBar={
            NavigationBar(containerColor=Panel,tonalElevation=0.dp) {
                primary.forEach { s ->
                    NavigationBarItem(selected=screen==s,onClick={screen=s},icon={Icon(s.icon,null,Modifier.size(20.dp))},label={Text(s.title,fontSize=9.sp)},colors=NavigationBarItemDefaults.colors(selectedIconColor=Bg,selectedTextColor=Cyan,indicatorColor=Cyan,unselectedIconColor=Quiet,unselectedTextColor=Quiet))
                }
            }
        }
    ) { pad ->
        when(screen) {
            Screen.HOME -> HomeScreen(Modifier.padding(pad),mode,{mode=it},projects.size,concepts.size,{screen=Screen.BUILDER},{screen=Screen.PROJECTS},{screen=Screen.AI})
            Screen.PROJECTS -> ProjectsScreen(Modifier.padding(pad),projects,projectName,{projectName=it},{ if(projectName.isNotBlank()){projects=listOf(ProjectData(projectName.trim(),"Short video","YouTube"))+projects;projectName=""} })
            Screen.ASSETS -> SimpleListScreen(Modifier.padding(pad),"Asset Library","Manage source footage, audio, images and exports.",listOf("Video footage","Audio recordings","Images & thumbnails","Exports & deliverables"),"No media has been imported in this local preview.")
            Screen.MEDIA -> MediaScreen(Modifier.padding(pad))
            Screen.PLATFORMS -> PlatformsScreen(Modifier.padding(pad))
            Screen.OPPORTUNITIES -> SimpleListScreen(Modifier.padding(pad),"Opportunities","A workspace for briefs, collaborations and creator opportunities.",listOf("Brand collaboration tracker","Campaign opportunities","Creator brief inbox","Application status"),"Opportunity services are not connected.")
            Screen.BUILDER -> BuilderScreen(Modifier.padding(pad),idea,{idea=it},hook,{hook=it},{if(idea.isNotBlank()){concepts=listOf(Concept(idea.trim(),hook.trim()))+concepts;idea="";hook="";screen=Screen.PROJECTS}})
            Screen.VISUAL -> VisualScreen(Modifier.padding(pad))
            Screen.AI -> AiToolsScreen(Modifier.padding(pad),selectedTool,{selectedTool=it})
            Screen.PLAN -> PlanScreen(Modifier.padding(pad))
            Screen.PROFILE -> ProfileScreen(Modifier.padding(pad),mode,{mode=it},{screen=Screen.PLAN},{screen=Screen.PLATFORMS})
        }
    }
}

@Composable
private fun PageTitle(title:String,subtitle:String) {
    Column(verticalArrangement=Arrangement.spacedBy(4.dp)) {
        Text(title,color=Ink,fontSize=25.sp,fontWeight=FontWeight.Bold)
        Text(subtitle,color=Muted,fontSize=12.sp,lineHeight=17.sp)
    }
}

@Composable
private fun HomeScreen(modifier:Modifier,mode:String,setMode:(String)->Unit,projectCount:Int,conceptCount:Int,create:()->Unit,projects:()->Unit,tools:()->Unit) {
    LazyColumn(modifier.fillMaxSize().padding(horizontal=17.dp),contentPadding=PaddingValues(vertical=15.dp),verticalArrangement=Arrangement.spacedBy(13.dp)) {
        item { PageTitle("Good day, Creator","Your creative workflow, directed from one workspace.") }
        item {
            PanelCard {
                SectionHeading("EXECUTION MODE","CREATOR CONTROL")
                Row(Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(Bg).padding(4.dp)) {
                    listOf("MANUAL","COPILOT","AUTO").forEach { m ->
                        Box(Modifier.weight(1f).clip(RoundedCornerShape(9.dp)).background(if(mode==m) Cyan.copy(alpha=.16f) else Color.Transparent).clickable(enabled=m!="AUTO"){setMode(m)}.padding(vertical=11.dp),contentAlignment=Alignment.Center) {
                            Text(m,color=if(mode==m) Cyan else Muted,fontSize=10.sp,fontWeight=FontWeight.Bold)
                        }
                    }
                }
                Text("AUTO is locked until explicit creator consent.",color=Quiet,fontSize=10.sp)
            }
        }
        item {
            Column(Modifier.fillMaxWidth().clip(RoundedCornerShape(19.dp)).background(Brush.linearGradient(listOf(Color(0xFF10334C),Color(0xFF25234C)))).border(1.dp,Line.copy(alpha=.7f),RoundedCornerShape(19.dp)).padding(17.dp),verticalArrangement=Arrangement.spacedBy(9.dp)) {
                Row(verticalAlignment=Alignment.CenterVertically) { Text("TODAY'S CREATOR BRIEF",color=Cyan,fontSize=10.sp,fontWeight=FontWeight.Bold,letterSpacing=1.sp); Spacer(Modifier.weight(1f)); StatusPill("READY",Emerald) }
                Text("One idea. A complete creative workflow.",color=Ink,fontSize=21.sp,fontWeight=FontWeight.Bold,lineHeight=27.sp)
                Text("Plan  /  Create  /  Organize  /  Review",color=Muted,fontSize=12.sp)
                Button(onClick=create,modifier=Modifier.fillMaxWidth(),shape=RoundedCornerShape(11.dp),colors=ButtonDefaults.buttonColors(containerColor=Cyan,contentColor=Bg)) { Text("CREATE A NEW CONCEPT",fontWeight=FontWeight.Black,fontSize=11.sp) }
            }
        }
        item {
            Row(horizontalArrangement=Arrangement.spacedBy(8.dp)) {
                MetricCard("PROJECTS",projectCount.toString(),"In workspace",Modifier.weight(1f))
                MetricCard("CONCEPTS",conceptCount.toString(),"Saved locally",Modifier.weight(1f))
                MetricCard("PLATFORMS","0 / 4","Not connected",Modifier.weight(1f))
            }
        }
        item { PanelCard {
            SectionHeading("NEXT BEST ACTION","01")
            Text("Shape your next content idea",color=Ink,fontSize=17.sp,fontWeight=FontWeight.Bold)
            Text("Build the hook, story structure and platform-ready draft before production.",color=Muted,fontSize=12.sp,lineHeight=18.sp)
            ActionRow("Open Content Builder",Icons.Default.Edit,create)
        } }
        item { PanelCard {
            SectionHeading("WORKSPACE SHORTCUTS","QUICK ACCESS")
            ActionRow("Projects & concepts",Icons.Default.Folder,projects)
            ActionRow("AI creator tools",Icons.Default.AutoAwesome,tools)
            ActionRow("Platform connections",Icons.Default.Share,{})
        } }
        item { PanelCard {
            SectionHeading("PLATFORM PULSE","0 / 4 CONNECTED")
            listOf("YouTube","Facebook","Instagram","TikTok").forEach { PlatformRow(it,"Not connected") }
        } }
        item { Text("CREVANTIQ DIRECTOR  •  BUILD 0.7.6",color=Quiet,fontSize=9.sp,modifier=Modifier.fillMaxWidth().padding(vertical=8.dp)) }
    }
}

@Composable
private fun ProjectsScreen(modifier:Modifier,projects:List<ProjectData>,name:String,setName:(String)->Unit,add:()->Unit) {
    LazyColumn(modifier.fillMaxSize().padding(horizontal=17.dp),contentPadding=PaddingValues(vertical=16.dp),verticalArrangement=Arrangement.spacedBy(12.dp)) {
        item { PageTitle("Projects","Organize content production from idea to delivery.") }
        item { PanelCard {
            SectionHeading("NEW PROJECT","LOCAL DRAFT")
            OutlinedTextField(value=name,onValueChange=setName,modifier=Modifier.fillMaxWidth(),singleLine=true,label={Text("Project name")},placeholder={Text("e.g. Baul Frame Stories")},colors=FieldColors(),shape=RoundedCornerShape(12.dp))
            PrimaryButton("ADD PROJECT",add,name.isNotBlank())
        } }
        item { SectionHeading("YOUR PROJECTS",projects.size.toString()+" TOTAL") }
        items(projects) { p -> PanelCard {
            Row(verticalAlignment=Alignment.CenterVertically) {
                Icon(Icons.Default.FolderOpen,null,tint=Cyan,modifier=Modifier.size(28.dp))
                Spacer(Modifier.width(11.dp))
                Column(Modifier.weight(1f)) { Text(p.name,color=Ink,fontSize=14.sp,fontWeight=FontWeight.Bold); Text(p.format+"  •  "+p.platform,color=Muted,fontSize=10.sp) }
                StatusPill("DRAFT",Amber)
            }
        } }
        item { InfoNote("Projects are currently stored in app memory. Cloud sync will be added after the new backend is connected.") }
    }
}

@Composable
private fun BuilderScreen(modifier:Modifier,idea:String,setIdea:(String)->Unit,hook:String,setHook:(String)->Unit,save:()->Unit) {
    LazyColumn(modifier.fillMaxSize().padding(horizontal=17.dp),contentPadding=PaddingValues(vertical=16.dp),verticalArrangement=Arrangement.spacedBy(12.dp)) {
        item { PageTitle("Content Builder","Turn a raw thought into a structured content brief.") }
        item { PanelCard {
            SectionHeading("01  /  CORE IDEA","REQUIRED")
            OutlinedTextField(value=idea,onValueChange=setIdea,modifier=Modifier.fillMaxWidth(),label={Text("What do you want to create?")},placeholder={Text("Describe the story or content idea…")},minLines=3,colors=FieldColors(),shape=RoundedCornerShape(12.dp))
            SectionHeading("02  /  OPENING HOOK","OPTIONAL")
            OutlinedTextField(value=hook,onValueChange=setHook,modifier=Modifier.fillMaxWidth(),label={Text("First 3 seconds")},placeholder={Text("Write the opening line…")},minLines=2,colors=FieldColors(),shape=RoundedCornerShape(12.dp))
            PrimaryButton("SAVE CONTENT CONCEPT",save,idea.isNotBlank())
        } }
        item { PanelCard {
            SectionHeading("PRODUCTION PIPELINE","6 STAGES")
            listOf("Creative brief","Hook & story structure","Script and CTA","Media & subtitles","Review and approval","Platform-ready draft").forEachIndexed { i,s -> PipelineRow(i+1,s) }
        } }
        item { InfoNote("This build saves concepts locally. AI generation, media processing and publishing are not active yet.") }
    }
}

@Composable
private fun AiToolsScreen(modifier:Modifier,selected:String,onSelect:(String)->Unit) {
    val tools=listOf(
        Triple("Idea Director","Structure raw ideas into creative briefs.",Icons.Default.Lightbulb),
        Triple("Script & Hook","Develop hooks, story beats and CTAs.",Icons.Default.Article),
        Triple("Thumbnail Studio","Plan 16:9 and 9:16 visual concepts.",Icons.Default.Image),
        Triple("Platform Optimizer","Prepare format-specific content drafts.",Icons.Default.Tune),
        Triple("Subtitle Assistant","Plan transcription and subtitle workflows.",Icons.Default.Subtitles),
        Triple("Creator Chat","A central place for creative direction.",Icons.Default.Chat)
    )
    LazyColumn(modifier.fillMaxSize().padding(horizontal=17.dp),contentPadding=PaddingValues(vertical=16.dp),verticalArrangement=Arrangement.spacedBy(10.dp)) {
        item { PageTitle("AI Tools","A unified creative toolset. Provider execution is not connected.") }
        item { PanelCard {
            SectionHeading("AI PROVIDER ORCHESTRATOR","NOT CONFIGURED")
            Text("CREVANTIQ AI ROUTING",color=Ink,fontSize=16.sp,fontWeight=FontWeight.Bold)
            Text("Provider credentials and model routing will be configured securely on the new backend.",color=Muted,fontSize=11.sp,lineHeight=17.sp)
            StatusPill("WAITING FOR BACKEND",Amber)
        } }
        items(tools) { t -> PanelCard {
            Row(verticalAlignment=Alignment.CenterVertically) {
                Box(Modifier.size(42.dp).clip(RoundedCornerShape(12.dp)).background(Cyan.copy(alpha=.12f)),contentAlignment=Alignment.Center) { Icon(t.third,null,tint=Cyan) }
                Spacer(Modifier.width(11.dp))
                Column(Modifier.weight(1f)) { Text(t.first,color=Ink,fontSize=13.sp,fontWeight=FontWeight.Bold); Text(t.second,color=Muted,fontSize=10.sp) }
                TextButton(onClick={onSelect(t.first)}) { Text(if(selected==t.first) "SELECTED" else "VIEW",color=Cyan,fontSize=9.sp) }
            }
            if(selected==t.first) Text("Selected tool • provider connection pending",color=Emerald,fontSize=10.sp)
        } }
        item { InfoNote("No AI provider is called from this screen. No API key or secret is stored in the Android app.") }
    }
}

@Composable
private fun PlatformsScreen(modifier:Modifier) {
    LazyColumn(modifier.fillMaxSize().padding(horizontal=17.dp),contentPadding=PaddingValues(vertical=16.dp),verticalArrangement=Arrangement.spacedBy(11.dp)) {
        item { PageTitle("Connected Platforms","Official OAuth connections will be managed through the new backend.") }
        item { PanelCard { SectionHeading("ACCOUNT CONNECTIONS","0 / 4"); Text("Connect your creator channels securely when OAuth setup is complete.",color=Muted,fontSize=12.sp,lineHeight=18.sp) } }
        items(listOf("YouTube","Facebook","Instagram","TikTok")) { name -> PanelCard {
            PlatformRow(name,"Not connected")
            Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.End) { OutlinedButton(onClick={},enabled=false,shape=RoundedCornerShape(9.dp)) { Text("CONNECT — COMING SOON",fontSize=9.sp,color=Quiet) } }
        } }
        item { InfoNote("No legacy OAuth clients, tokens or provider secrets are included in this build.") }
    }
}

@Composable
private fun MediaScreen(modifier:Modifier) {
    LazyColumn(modifier.fillMaxSize().padding(horizontal=17.dp),contentPadding=PaddingValues(vertical=16.dp),verticalArrangement=Arrangement.spacedBy(11.dp)) {
        item { PageTitle("Media & Subtitles","Prepare source assets, transcripts and subtitle deliverables.") }
        item { PanelCard { SectionHeading("MEDIA WORKSPACE","EMPTY")
            listOf("Video and audio source files","Image and thumbnail assets","Subtitle tracks (SRT)","Exports and final deliverables").forEach { ActionRow(it,Icons.Default.InsertDriveFile,{}) }
        } }
        item { PanelCard { SectionHeading("SUBTITLE WORKFLOW","PLANNED")
            PipelineRow(1,"Select an audio or video source")
            PipelineRow(2,"Generate or import transcript")
            PipelineRow(3,"Review actual words and timestamps")
            PipelineRow(4,"Export SRT and subtitle file")
        } }
        item { InfoNote("File import, transcription and export actions are not yet wired in this preview.") }
    }
}

@Composable
private fun VisualScreen(modifier:Modifier) {
    LazyColumn(modifier.fillMaxSize().padding(horizontal=17.dp),contentPadding=PaddingValues(vertical=16.dp),verticalArrangement=Arrangement.spacedBy(11.dp)) {
        item { PageTitle("Visual Studio","A visual planning space for thumbnails and creative assets.") }
        item { PanelCard {
            SectionHeading("CANVAS PRESETS","PREVIEW")
            Row(horizontalArrangement=Arrangement.spacedBy(9.dp)) {
                AspectCard("16:9","YouTube",Modifier.weight(1f),16f/9f)
                AspectCard("9:16","Shorts / Reels",Modifier.weight(1f),9f/16f)
            }
        } }
        item { PanelCard {
            SectionHeading("VISUAL SYSTEM","BRAND READY")
            listOf("Brand palette","Typography hierarchy","Thumbnail composition","Safe-area planning","Export specifications").forEach { ActionRow(it,Icons.Default.Palette,{}) }
        } }
        item { InfoNote("Image generation and export are not connected. This screen is a layout and workflow preview.") }
    }
}

@Composable
private fun PlanScreen(modifier:Modifier) {
    LazyColumn(modifier.fillMaxSize().padding(horizontal=17.dp),contentPadding=PaddingValues(vertical=16.dp),verticalArrangement=Arrangement.spacedBy(11.dp)) {
        item { PageTitle("Plan & Credits","Subscription entitlements and AI usage overview.") }
        item { PanelCard {
            SectionHeading("CURRENT PLAN","PREVIEW")
            Text("FREE",color=Ink,fontSize=26.sp,fontWeight=FontWeight.Black)
            Text("Plan status is not yet loaded from the new backend.",color=Muted,fontSize=11.sp)
            StatusPill("NOT CONFIGURED",Amber)
        } }
        item { PanelCard {
            SectionHeading("AI CREDIT BALANCE","—")
            Text("Credit wallet pending setup",color=Ink,fontSize=15.sp,fontWeight=FontWeight.Bold)
            Text("Usage will be recorded through a server-side credit ledger. No credits are being charged in this preview.",color=Muted,fontSize=11.sp,lineHeight=17.sp)
        } }
        item { SectionHeading("PLAN OPTIONS","PLANNED") }
        items(listOf("FREE","PRO","BOOST","PLUS","ULTRA")) { plan -> PanelCard {
            Row(verticalAlignment=Alignment.CenterVertically) { Text(plan,color=Ink,fontSize=14.sp,fontWeight=FontWeight.Bold,modifier=Modifier.weight(1f)); StatusPill(if(plan=="FREE") "PREVIEW" else "COMING SOON",if(plan=="FREE") Emerald else Quiet) }
        } }
        item { InfoNote("No checkout, billing or real payment processing is enabled.") }
    }
}

@Composable
private fun ProfileScreen(modifier:Modifier,mode:String,setMode:(String)->Unit,plan:()->Unit,platforms:()->Unit) {
    LazyColumn(modifier.fillMaxSize().padding(horizontal=17.dp),contentPadding=PaddingValues(vertical=16.dp),verticalArrangement=Arrangement.spacedBy(11.dp)) {
        item { PageTitle("Creator Profile","Personal workspace settings and account controls.") }
        item { PanelCard {
            Row(verticalAlignment=Alignment.CenterVertically) {
                Box(Modifier.size(54.dp).clip(CircleShape).background(Brush.linearGradient(listOf(Cyan,Violet))),contentAlignment=Alignment.Center) { Text("C",color=Bg,fontSize=24.sp,fontWeight=FontWeight.Black) }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) { Text("Creator Workspace",color=Ink,fontSize=15.sp,fontWeight=FontWeight.Bold); Text("Local preview • account setup pending",color=Muted,fontSize=10.sp) }
                StatusPill("GUEST",Amber)
            }
        } }
        item { PanelCard {
            SectionHeading("CREATOR MODE","CURRENT")
            listOf("MANUAL","COPILOT","AUTO").forEach { m ->
                Row(Modifier.fillMaxWidth().clip(RoundedCornerShape(9.dp)).clickable(enabled=m!="AUTO"){setMode(m)}.padding(vertical=10.dp),verticalAlignment=Alignment.CenterVertically) {
                    Text(m,color=if(mode==m) Cyan else Ink,fontSize=12.sp,fontWeight=FontWeight.Bold,modifier=Modifier.weight(1f))
                    if(mode==m) StatusPill("ACTIVE",Emerald) else if(m=="AUTO") StatusPill("LOCKED",Quiet)
                }
            }
        } }
        item { PanelCard {
            ActionRow("Connected platforms",Icons.Default.Share,platforms)
            ActionRow("Subscription & credits",Icons.Default.WorkspacePremium,plan)
            ActionRow("Devices & security",Icons.Default.Security,{})
            ActionRow("Language & region  •  bn-BD / Asia-Dhaka",Icons.Default.Language,{})
            ActionRow("Notifications",Icons.Default.Notifications,{})
        } }
        item { InfoNote("Authentication, device registration and account changes are not active until the new backend integration is complete.") }
    }
}

@Composable
private fun SimpleListScreen(modifier:Modifier,title:String,subtitle:String,rows:List<String>,empty:String) {
    LazyColumn(modifier.fillMaxSize().padding(horizontal=17.dp),contentPadding=PaddingValues(vertical=16.dp),verticalArrangement=Arrangement.spacedBy(11.dp)) {
        item { PageTitle(title,subtitle) }
        items(rows) { row -> PanelCard { ActionRow(row,Icons.Default.ChevronRight,{}) } }
        item { InfoNote(empty) }
    }
}

@Composable
private fun PanelCard(content:@Composable ColumnScope.()->Unit) {
    Column(Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(Panel).border(1.dp,Line,RoundedCornerShape(16.dp)).padding(14.dp),verticalArrangement=Arrangement.spacedBy(10.dp),content=content)
}
@Composable private fun SectionHeading(title:String,meta:String) {
    Row(Modifier.fillMaxWidth(),verticalAlignment=Alignment.CenterVertically) {
        Text(title,color=Cyan,fontSize=9.sp,fontWeight=FontWeight.Bold,letterSpacing=.8.sp,modifier=Modifier.weight(1f))
        Text(meta,color=Quiet,fontSize=8.sp,fontWeight=FontWeight.Bold)
    }
}
@Composable private fun MetricCard(title:String,value:String,detail:String,modifier:Modifier) {
    Column(modifier.clip(RoundedCornerShape(13.dp)).background(Panel).border(1.dp,Line,RoundedCornerShape(13.dp)).padding(10.dp)) {
        Text(title,color=Muted,fontSize=8.sp,fontWeight=FontWeight.Bold)
        Text(value,color=Ink,fontSize=19.sp,fontWeight=FontWeight.Bold,modifier=Modifier.padding(top=5.dp))
        Text(detail,color=Quiet,fontSize=8.sp,maxLines=1,overflow=TextOverflow.Ellipsis)
    }
}
@Composable private fun StatusPill(text:String,color:Color) {
    Box(Modifier.clip(RoundedCornerShape(30.dp)).background(color.copy(alpha=.13f)).padding(horizontal=8.dp,vertical=5.dp)) { Text(text,color=color,fontSize=8.sp,fontWeight=FontWeight.Bold) }
}
@Composable private fun ActionRow(title:String,icon:ImageVector,onClick:()->Unit) {
    Row(Modifier.fillMaxWidth().clip(RoundedCornerShape(9.dp)).clickable(onClick=onClick).padding(vertical=7.dp),verticalAlignment=Alignment.CenterVertically) {
        Icon(icon,null,tint=Cyan,modifier=Modifier.size(19.dp)); Spacer(Modifier.width(10.dp))
        Text(title,color=Ink,fontSize=12.sp,modifier=Modifier.weight(1f)); Icon(Icons.Default.ChevronRight,null,tint=Quiet,modifier=Modifier.size(17.dp))
    }
}
@Composable private fun PlatformRow(name:String,status:String) {
    Row(Modifier.fillMaxWidth().padding(vertical=5.dp),verticalAlignment=Alignment.CenterVertically) {
        Box(Modifier.size(8.dp).clip(CircleShape).background(Quiet)); Spacer(Modifier.width(10.dp))
        Text(name,color=Ink,fontSize=12.sp,modifier=Modifier.weight(1f)); Text(status,color=Muted,fontSize=10.sp)
    }
}
@Composable private fun PipelineRow(index:Int,label:String) {
    Row(Modifier.fillMaxWidth().padding(vertical=3.dp),verticalAlignment=Alignment.CenterVertically) {
        Box(Modifier.size(25.dp).clip(CircleShape).background(Cyan.copy(alpha=.13f)),contentAlignment=Alignment.Center) { Text("%02d".format(index),color=Cyan,fontSize=9.sp,fontWeight=FontWeight.Bold) }
        Spacer(Modifier.width(10.dp)); Text(label,color=Ink,fontSize=11.sp)
    }
}
@Composable private fun PrimaryButton(label:String,onClick:()->Unit,enabled:Boolean=true) {
    Button(onClick=onClick,enabled=enabled,modifier=Modifier.fillMaxWidth().height(45.dp),shape=RoundedCornerShape(10.dp),colors=ButtonDefaults.buttonColors(containerColor=Cyan,contentColor=Bg,disabledContainerColor=Surface,disabledContentColor=Quiet)) { Text(label,fontSize=10.sp,fontWeight=FontWeight.Black,letterSpacing=.5.sp) }
}
@Composable private fun InfoNote(text:String) {
    Row(Modifier.fillMaxWidth().clip(RoundedCornerShape(11.dp)).background(Surface.copy(alpha=.7f)).padding(11.dp),verticalAlignment=Alignment.Top) {
        Icon(Icons.Default.Info,null,tint=Cyan,modifier=Modifier.size(15.dp)); Spacer(Modifier.width(8.dp)); Text(text,color=Muted,fontSize=10.sp,lineHeight=15.sp)
    }
}
@Composable private fun FieldColors()=OutlinedTextFieldDefaults.colors(focusedBorderColor=Cyan,unfocusedBorderColor=Line,focusedTextColor=Ink,unfocusedTextColor=Ink,focusedLabelColor=Cyan,unfocusedLabelColor=Muted,cursorColor=Cyan,focusedContainerColor=Bg,unfocusedContainerColor=Bg)
@Composable private fun AspectCard(label:String,caption:String,modifier:Modifier,ratio:Float) {
    Column(modifier,verticalArrangement=Arrangement.spacedBy(6.dp)) {
        Box(Modifier.fillMaxWidth().aspectRatio(ratio).clip(RoundedCornerShape(10.dp)).background(Brush.linearGradient(listOf(Color(0xFF143A55),Color(0xFF28244D)))).border(1.dp,Cyan.copy(alpha=.45f),RoundedCornerShape(10.dp)),contentAlignment=Alignment.Center) { Text(label,color=Ink,fontSize=15.sp,fontWeight=FontWeight.Bold) }
        Text(caption,color=Muted,fontSize=9.sp)
    }
}
