plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.crevantiq.director"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.crevantiq.director"
        minSdk = 26
        targetSdk = 35
        versionCode = 8
        versionName = "0.7.6"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
        debug {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    val composeBom = platform("androidx.compose:compose-bom:2024.10.01")
    implementation(composeBom)
    androidXComposeUi()
    androidXComposeGraphics()
    androidXComposeUiToolingPreview()
    androidXComposeMaterial3()

    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation("androidx.activity:activity-compose:1.9.3")

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}

fun ComponentDependencies.androidXComposeUi() =
    implementation("androidx.compose.ui:ui")

fun ComponentDependencies.androidXComposeGraphics() =
    implementation("androidx.compose.ui:ui-graphics")

fun ComponentDependencies.androidXComposeUiToolingPreview() =
    implementation("androidx.compose.ui:ui-tooling-preview")

fun ComponentDependencies.androidXComposeMaterial3() =
    implementation("androidx.compose.material3:material3")
