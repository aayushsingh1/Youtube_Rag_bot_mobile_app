plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.myyoutuberagbot"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myyoutuberagbot"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") { // Use create() to define a signing config
            storeFile = file("/home/aayush/key_store_1") // Use '=' instead of space
            storePassword = System.getenv("KEYSTORE_PASSWORD") ?: "xxx" // Use '=' instead of space
            keyAlias = "key_1" // Use '=' instead of space
            keyPassword = System.getenv("KEYSTORE_PASSWORD") ?: "xxx" // Use '=' instead of space
        }
    }

    buildTypes {
        getByName("release") { // Use getByName() to access the release build type
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release") // Use '=' instead of space
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
