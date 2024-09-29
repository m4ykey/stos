// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version("8.5.2") apply false
    id("org.jetbrains.kotlin.android") version("2.0.20") apply false
    id("com.google.gms.google-services") version("4.4.2") apply false
    id("com.google.firebase.crashlytics") version("3.0.2") apply false
    id("org.jetbrains.kotlin.plugin.compose") version("2.1.0-Beta1") apply false
    id("com.google.firebase.firebase-perf") version("1.4.2") apply false
    id("com.android.library") version "8.6.1" apply false
    kotlin("plugin.serialization") version "2.0.20" apply false
}