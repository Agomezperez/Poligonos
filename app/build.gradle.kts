plugins {
    id("com.android.application")
    id("kotlin-android")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.poligonos"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.poligonos"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        getByName("debug") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures{
        viewBinding = true
        dataBinding = true

    }

    viewBinding {
        enable = true
    }

}

dependencies {
    implementation ("org.jetbrains.kotlin:kotlin-stdlib:1.6.20")
    implementation ("androidx.core:core-ktx:1.8.0")
    implementation ("androidx.appcompat:appcompat:1.4.2")
    implementation ("com.google.android.material:material:1.5.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("androidx.legacy:legacy-support-v4:1.0.0")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")
    //
    implementation (("com.google.code.gson:gson:2.8.9"))
    implementation ("androidx.activity:activity-ktx:1.4.0")
    implementation ("androidx.fragment:fragment-ktx:1.4.1")

    implementation ("androidx.recyclerview:recyclerview-selection:1.1.0")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.3")

    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation ("id.zelory:compressor:3.0.1")

    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.4.1")

    implementation ("androidx.navigation:navigation-fragment-ktx:2.4.1")
    implementation ("androidx.navigation:navigation-ui-ktx:2.4.1")

    implementation ("androidx.room:room-runtime:2.4.2")
    implementation ("androidx.room:room-ktx:2.4.2")
    kapt ("androidx.room:room-compiler:2.4.2")

    implementation ("com.jakewharton.timber:timber:5.0.1")

    implementation ("androidx.work:work-runtime-ktx:2.7.1")

    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")
    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")

    implementation ("com.google.dagger:hilt-android:2.40.4")
    kapt ("com.google.dagger:hilt-android-compiler:2.40.4")
    kapt ("com.google.dagger:dagger-android-processor:2.40.4")

    implementation ("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
    implementation ("androidx.hilt:hilt-work:1.0.0")
    kapt ("androidx.hilt:hilt-compiler:1.0.0")

    implementation("androidx.datastore:datastore:1.0.0")
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    implementation ("com.google.android.flexbox:flexbox:3.0.0")

    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")

    implementation ("androidx.browser:browser:1.3.0")

    implementation ("androidx.ui:ui-tooling:0.1.0-dev17")
    implementation ("androidx.ui:ui-layout:0.1.0-dev14")
    implementation ("androidx.ui:ui-material:0.1.0-dev14")
}