package com.crevantiq.director.model

data class User(
    val id: String,
    val email: String,
    val fullName: String,
    val role: String = "creator",
    val creatorSetupComplete: Boolean = false,
    val createdAt: String = ""
)

data class CreatorProfile(
    val channelName: String,
    val channelDescription: String,
    val channelLogoRef: String,
    val country: String,
    val appLanguage: String,
    val primaryContentLanguage: String,
    val additionalContentLanguages: List<String> = emptyList()
)

data class DeviceSlot(
    val deviceId: String,
    val clientType: String,
    val deviceLabel: String,
    val lastActiveAt: String
)

data class SessionState(
    val isAuthenticated: Boolean = false,
    val user: User? = null,
    val profile: CreatorProfile? = null,
    val token: String? = null,
    val activeDevices: List<DeviceSlot> = emptyList(),
    val isDemoMode: Boolean = false
)
