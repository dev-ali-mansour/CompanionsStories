plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.kotlin.ksp)
}

val dynamicVersionCode: Int? = System.getenv("VERSION_CODE")?.toIntOrNull()
val dynamicVersionName: String? = System.getenv("VERSION_NAME")

android {
    compileSdk = 36
    defaultConfig {
        applicationId = "com.tibadev.alimansour.companionsstories"
        minSdk = 23
        targetSdk = 36
        multiDexEnabled = true
        vectorDrawables.useSupportLibrary = true
        versionCode = dynamicVersionCode ?: 7
        versionName = dynamicVersionName ?: "2.4.2"
    }
    buildTypes {
        getByName("release") {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
                "proguard-kotlin-serialization.pro",
            )
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    namespace = "com.tibadev.alimansour.companionsstories"
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.koin)
    ksp(libs.koin.compiler)
    implementation(libs.timber)
    implementation(libs.kotlinx.serialization.json)
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)
    ksp(libs.koin.compiler)

    implementation(libs.androidx.core)
    implementation(libs.bundles.lifecycle)
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.bundles.coil)
    implementation(libs.navigation.compose)
    implementation(libs.bundles.appcompat)
    implementation(libs.app.update)
    implementation(libs.browser)
    implementation(libs.multidex)
    implementation(libs.splashScreen)
    implementation(libs.play.services.ads)

    testImplementation(libs.bundles.domain.test)
    androidTestImplementation(libs.bundles.app.test)
    debugImplementation(libs.test.compose.ui.test.junit4)
    
    implementation("com.google.android.material:material:1.13.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
}


kotlin {
    jvmToolchain(JavaVersion.VERSION_21.majorVersion.toInt())
}