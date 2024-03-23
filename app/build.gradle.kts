import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.safeargs)
    alias(libs.plugins.gms)
    alias(libs.plugins.firebase.crashlytics)
}

val versionMajor = 0
val versionMinor = 1
val versionPatch = 0
val versionBuild = 1
var versionExt = ""

if (versionBuild > 0) {
    versionExt = ".${versionBuild}-beta"
}

android {
    namespace = "com.m4ykey.stos"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.m4ykey.stos"
        minSdk = 26
        targetSdk = 34
        versionCode = versionMajor * 1000000 + versionMinor * 10000 + versionPatch * 100 + versionBuild
        versionName = "${versionMajor}.${versionMinor}.${versionPatch}${versionExt}"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    val apiKey by lazy {
        rootProject.file("local.properties").inputStream().use { input ->
            Properties().apply { load(input) }
        }
    }

    buildTypes.all {
        buildConfigField("String", "STACK_API_KEY", "\"${apiKey.getProperty("STACK_API_KEY")}\"")
    }

    configurations {
        create("cleanedAnnotations")
        implementation.get().exclude(group = "org.jetbrains", module = "annotations")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.android.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.paging)
    implementation(libs.androidx.swiperefreshlayout)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    implementation(libs.bundles.navigation)
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.retrofit)
    implementation(libs.bundles.okhttp)

    implementation(libs.moshi)

    implementation(libs.coil)

    implementation(libs.firebase.crashlytics)

    implementation(libs.jsoup)

    implementation("io.noties.markwon:core:4.6.2")

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso)

}