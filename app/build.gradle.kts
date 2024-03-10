import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

val versionMajor = 0
val versionMinor = 0
val versionPatch = 2
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

    val navVer = "2.7.7"
    val hilt = "2.50"
    val lifecycle = "2.7.0"
    val retrofit = "2.9.0"
    val markwon = "4.6.2"

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation("androidx.navigation:navigation-fragment-ktx:$navVer")
    implementation("androidx.navigation:navigation-ui-ktx:$navVer")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("com.squareup.retrofit2:retrofit:$retrofit")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofit")

    implementation("com.google.dagger:hilt-android:$hilt")
    ksp("com.google.dagger:hilt-android-compiler:$hilt")

    implementation("androidx.paging:paging-runtime-ktx:3.2.1")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle")

    implementation("com.squareup.moshi:moshi-kotlin:1.15.0")

    implementation("io.coil-kt:coil:2.5.0")

    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.12.0"))
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")

    implementation("com.google.firebase:firebase-crashlytics:18.6.2")

    implementation("io.noties.markwon:core:$markwon")
    implementation("io.noties.markwon:image-coil:$markwon")
    implementation("io.noties.markwon:html:$markwon")
    implementation("io.noties.markwon:linkify:$markwon")
    implementation("io.noties.markwon:inline-parser:$markwon")
    implementation("io.noties.markwon:image:$markwon")
    implementation("io.noties.markwon:editor:$markwon")
    implementation("io.noties.markwon:ext-latex:$markwon")
    implementation("io.noties.markwon:ext-strikethrough:$markwon")
    implementation("io.noties.markwon:ext-tables:$markwon")
    implementation("io.noties.markwon:ext-tasklist:$markwon")
    implementation("io.noties.markwon:recycler:$markwon")
    implementation("io.noties.markwon:syntax-highlight:$markwon")

}