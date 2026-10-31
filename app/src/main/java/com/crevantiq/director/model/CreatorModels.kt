package com.crevantiq.director.model

data class DailyBrief(
    val status: String = "READY",
    val summary: String,
    val highlights: List<String>,
    val focusPlatforms: List<String>,
    val creatorHealthScore: Int = 94
)

data class NextBestAction(
    val actionTitle: String,
    val platform: String,
    val expectedImpact: String,
    val rationale: String,
    val urgency: String = "HIGH"
)

data class WeeklyPlanItem(
    val id: String,
    val day: String,
    val platform: String,
    val contentType: String,
    val titleTheme: String,
    val status: String = "SCHEDULED"
)

data class Project(
    val id: String,
    val title: String,
    val coreIdea: String,
    val state: String = "READY", // READY, IN_PROGRESS, NEEDS_REVIEW, SCHEDULED, PUBLISHED
    val targetPlatforms: List<String> = listOf("youtube", "tiktok", "instagram"),
    val format: String = "Short-form Video",
    val createdAt: String = "",
    val variants: List<ProjectVariant> = emptyList()
)

data class ProjectVariant(
    val platform: String,
    val title: String,
    val hook: String,
    val description: String,
    val tags: List<String>,
    val format: String,
    val status: String = "READY"
)

data class MediaAsset(
    val id: String,
    val projectId: String,
    val kind: String, // VIDEO, AUDIO, IMAGE, RAW_FOOTAGE, THUMBNAIL
    val fileName: String,
    val fileSize: String,
    val state: String = "READY", // READY, PROCESSING, HOLD, FAILED
    val durationSeconds: Int = 0
)

data class SubtitleTrack(
    val id: String,
    val projectId: String,
    val language: String,
    val currentRevisionId: String = "rev-01",
    val status: String = "READY", // READY, AI_PROCESSING, EMPTY
    val sampleText: String = ""
)

data class PlatformInfo(
    val platform: String,
    val connectionStatus: String, // CONNECTED, NOT_CONFIGURED, DISCONNECTED
    val followers: String = "0",
    val engagementRate: String = "0.0%",
    val reach: String = "0",
    val lastSync: String = "Never"
)

data class Opportunity(
    val id: String,
    val title: String,
    val platform: String,
    val confidenceClass: String = "HIGH", // HIGH, MEDIUM, EXPERIMENTAL
    val estimatedLift: String,
    val reasonCodes: List<String>,
    val category: String = "Format Trend"
)

data class ContentItem(
    val id: String,
    val concept: String,
    val format: String,
    val language: String,
    val state: String = "READY", // READY, NEEDS_REVIEW, DRAFT, APPROVED
    val hook: String = "",
    val scriptBody: String = "",
    val cta: String = "",
    val hashtags: List<String> = emptyList()
)

data class VisualArtifact(
    val id: String,
    val contentId: String,
    val artifactType: String, // THUMBNAIL, COVER, STORY_STILL, BANNER
    val state: String = "READY", // READY, GENERATING, NEEDS_REVIEW, APPROVED
    val prompt: String,
    val styleTheme: String = "Cyberpunk / Tech Bold",
    val aspectRatio: String = "16:9"
)

data class AiProviderInfo(
    val id: String,
    val name: String,
    val status: String, // ACTIVE, NOT_CONFIGURED
    val capabilities: List<String>,
    val latencyMs: Int = 320,
    val tier: String = "Primary Router"
)

data class BalanceBucket(
    val plan: Int = 2500,
    val gift: Int = 500,
    val purchased: Int = 1000
) {
    val total: Int get() = plan + gift + purchased
}

data class PlanCreditsInfo(
    val planName: String = "CREVANTIQ PRO",
    val planStatus: String = "ACTIVE",
    val billingInterval: String = "Monthly",
    val balances: BalanceBucket = BalanceBucket(),
    val allowedAiPower: List<String> = listOf("Gemini 1.5 Pro", "Whisper Large v3", "Imagen 3 Ultra", "Multi-platform Sync")
)

data class DirectorChatMessage(
    val id: String,
    val isUser: Boolean,
    val message: String,
    val timestamp: String,
    val executionLog: String? = null
)
