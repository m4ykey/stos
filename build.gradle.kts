// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    val android = "8.7.3"
    id("com.android.application") version(android) apply false
    id("org.jetbrains.kotlin.android") version("2.1.0") apply false
    id("com.google.gms.google-services") version("4.4.2") apply false
    id("com.google.firebase.crashlytics") version("3.0.2") apply false
    id("org.jetbrains.kotlin.plugin.compose") version("2.1.0-RC") apply false
    id("com.google.firebase.firebase-perf") version("1.4.2") apply false
    id("com.android.library") version(android) apply false
    id("com.google.devtools.ksp") version("2.1.0-1.0.29")
    kotlin("plugin.serialization") version "2.0.21" apply false
    kotlin("jvm") version "2.0.21" apply false
}