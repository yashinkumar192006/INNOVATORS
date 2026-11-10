plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.trinetra.mobile.android"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.trinetra.mobile.android"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        // Enable Jetpack Compose compilation
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }

        kotlinOptions {
            jvmTarget = "1.18"
        }

        // Enable Compose
        buildFeatures {
            compose = true
        }
    }

    dependencies {
        // Hilt and Compose
        implementation("com.google.dagger:hilt-android:2.48")
        implementation("androidx.hilt:hilt-android:2.48")
        implementation("androidx.hilt:hilt-navigation-compose:2.48")

        // Material 3 Compose
        implementation("androidx.compose.material3:material3:1.2.0-rc01")
        implementation("androidx.compose.ui:ui:1.2.0-rc01")
        implementation("androidx.compose.runtime:runtime-livedata:1.2.0-rc01")
        implementation("androidx.compose.runtime:runtime-rxjava2:1.2.0-rc01")
        implementation("androidx.compose.runtime:runtime-ktx:1.2.0-rc01")
        implementation("androidx.compose.ui:ui-tooling-preview:1.2.0-rc01")

        // Kotlin Coroutines
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

        // Lifecycle and Nav
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
        implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
        implementation("androidx.navigation:navigation-compose:2.7.7")

        // OkHttp and Retrofit
        implementation("com.squareup.okhttp3:okhttp:4.12.0")
        implementation("com.squareup.retrofit2:retrofit:2.11.0")
        implementation("com.squareup.moshi:moshi:1.14.0")
        implementation("com.squareup.moshi:moshi-kotlin:1.14.0")

        // Room Database
        implementation("androidx.room:room-ktx:2.5.0")
        kapt("androidx.room:room-compiler:2.5.0")

        // DataStore
        implementation("androidx.datastore:datastore-preference:1.1.1")
        implementation("androidx.datastore:datastore-flow:1.1.1")

        // Testing
        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test:runner:1.5.0")
        androidTestImplementation("androidx.test:rules:1.5.0")
    }
}

plugins {
    id("kotlin-kapt")
}