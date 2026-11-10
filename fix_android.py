#!/usr/bin/env python
import sys
sys.stdout = open(1, 'w', encoding='utf-8', closefd=True)

# Fix AndroidManifest.xml
manifest_content = '''<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.trinetra.persona"
    android:versionCode="1"
    android:versionName="1.0">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:theme="@style/Theme.TRINETRA">
    </application>

</manifest>'''

with open('C:\\INNOVATORS\\INNOVATORS\\android\\app\\src\\main\\AndroidManifest.xml', 'w', encoding='utf-8') as f:
    f.write(manifest_content)

print("AndroidManifest.xml updated successfully")

# Create placeholder drawable
import os
drawable_dir = r'C:\\INNOVATORS\\INNOVATORS\\android\\app\\src\\main\\res\\drawable'
os.makedirs(drawable_dir, exist_ok=True)

# Create ic_launcher.xml placeholder
with open(os.path.join(drawable_dir, 'ic_launcher.xml'), 'w', encoding='utf-8') as f:
    f.write('<!-- TRINETRA IC Launcher Icon -->')

print("Drawable directory and placeholder created")

# Update build.gradle with proper dependencies
build_gradle_content = '''plugins {
    id 'com.android.application'
    id 'kotlin-android'
}

android {
    compileSdk 33

    defaultConfig {
        applicationId "com.trinetra.persona"
        minSdk 24
        targetSdk 33
        versionCode 1
        versionName "1.0"

        compileOptions {
            sourceCompatibility JavaVersion.VERSION_11
            targetCompatibility JavaVersion.VERSION_11
        }

        kotlinOptions {
            jvmTarget = "1.18"
        }

        buildFeatures {
            compose true
        }
    }
}

dependencies {
    implementation "androidx.core:core-ktx:1.13.0"
    implementation "androidx.lifecycle.lifecycle-viewmodel-ktx:2.6.1"
    implementation "androidx.navigation:navigation-fragment-ktx:2.7.7"
    implementation "androidx.navigation:navigation-compose-ktx:2.7.7"

    implementation "androidx.compose.material3:material3:1.2.0-rc01"
    implementation "androidx.compose.ui:ui:1.2.0-rc01"
    implementation "androidx.compose.ui:ui-graphics:1.2.0-rc01"
    implementation "androidx.compose.ui:ui-tooling-preview:1.2.0-rc01"
    implementation "androidx.compose.material:material:1.2.0-rc01"
    implementation "androidx.compose.runtime:runtime-livedata:1.2.0-rc01"
    implementation "androidx.compose.runtime:runtime-rxjava2:1.2.0-rc01"
    implementation "androidx.compose.runtime:runtime-ktx:1.2.0-rc01"
    implementation "androidx.compose.compiler:compiler:1.2.0-rc01"
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3"
}
'''

with open('C:\\INNOVATORS\\INNOVATORS\\android\\app\\build.gradle', 'w', encoding='utf-8') as f:
    f.write(build_gradle_content)

print("build.gradle updated successfully")