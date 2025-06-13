import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeFeatureFlag
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.aboutLibraries)
    alias(libs.plugins.room)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hotReload)
}

val versionProperties = Properties().apply {
    load(rootProject.file("version.properties").inputStream())
}

val versionMajor = versionProperties["versionMajor"].toString().toInt()
val versionMinor = versionProperties["versionMinor"].toString().toInt()
val versionPatch = versionProperties["versionPatch"].toString().toInt()

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    composeCompiler {
        featureFlags.add(ComposeFeatureFlag.OptimizeNonSkippingGroups)
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    jvm("desktop")

    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(compose.preview)
                implementation(libs.androidx.activity.compose)
                implementation(libs.kotlinx.coroutines.android)
                implementation(libs.ktor.client.okhttp)
                implementation(libs.ktor.client.android)
                implementation(libs.koin.android)
                implementation(libs.coil3.network.okhttp)
                implementation(libs.androidx.ui.tooling.preview)
                implementation(libs.androidx.browser)
                implementation(libs.bundles.androidx.paging)
            }
        }

        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(libs.androidx.lifecycle.viewmodel)
                implementation(libs.androidx.lifecycle.runtime.compose)
                implementation(libs.kotlinx.serialization)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.client.logging)
                implementation(libs.ktor.serialization)
                implementation(libs.ktor.client.cio)
                implementation(libs.koin.core)
                implementation(libs.koin.compose)
                implementation(libs.kotlinx.datetime)
                implementation(libs.coil3.compose)
                implementation(libs.koin.compose.viewmodel)
                implementation(libs.androidx.navigation)
                implementation(libs.coil3.network.ktor)
                implementation(libs.sqlite.bundled)
                implementation(libs.room.runtime)
                implementation(libs.bundles.markdown)
                implementation(libs.bundles.aboutlibraries)
                implementation(libs.androidx.icons.extended)
                implementation(libs.bundles.compottie)
                implementation(libs.cashapp.paging.common)
                implementation(libs.ktor.client.encoding)
            }
        }

        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(libs.kotlinx.coroutines.swing)
                implementation(libs.ktor.client.okhttp)
            }
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

android {
    namespace = "com.m4ykey.stos"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.m4ykey.stos"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = versionMajor * 1000000 + versionMinor * 10000 + versionPatch * 100
        versionName = "$versionMajor.$versionMinor.$versionPatch"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    debugImplementation(libs.androidx.ui.tooling)

    add("kspAndroid", libs.room.compiler)
}

compose.desktop {
    application {
        mainClass = "com.m4ykey.stos.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb, TargetFormat.Exe)
            packageName = "com.m4ykey.stos"
            packageVersion = "$versionMajor.$versionMinor.$versionPatch"
        }
    }
}