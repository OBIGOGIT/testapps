plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.ar2_test2"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.ar2_test2"
        minSdk = 30
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("DHU-AR2") {
            storeFile = file("../platform_geely.keystore")
            storePassword = "sv2655888"
            keyAlias = "desaysv"
            keyPassword = "sv2655888"
        }
    }
    buildTypes {
        debug {
            signingConfig = signingConfigs.getByName("DHU-AR2")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        signingConfig = signingConfigs.getByName("DHU-AR2")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(files("libs/ecarx.adaptapi.20250326.jar"))
    implementation(files("libs/android.car.jar"))
    implementation(files("libs/oneosapi-1.3.39.aar"))
    implementation("com.google.android.exoplayer:exoplayer:2.18.7") // 최신버전 사용 권장
    implementation("com.google.android.exoplayer:exoplayer-ui:2.18.7")
}