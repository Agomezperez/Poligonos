// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:8.0.0")

        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
        
        classpath("com.google.gms:google-services:4.3.10")


        classpath("com.google.dagger:hilt-android-gradle-plugin:2.40.1")

        // Add serialization library for add the plugin
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.6.10")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://maven.google.com") }
        maven { url = uri("https://maven.fabric.io/public") }
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}