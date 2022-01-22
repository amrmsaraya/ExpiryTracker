plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.google.devtools.ksp") version "1.6.10-1.0.2"
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = AndroidConfig.compileSdk

    defaultConfig {
        applicationId = "com.github.amrmsaraya.expirytracker"
        minSdk = AndroidConfig.minSdk
        targetSdk = AndroidConfig.targetSdk
        versionCode = AndroidConfig.versionCode
        versionName = AndroidConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = AndroidConfig.sourceCompatibility
        targetCompatibility = AndroidConfig.targetCompatibility
    }

    kotlinOptions {
        jvmTarget = AndroidConfig.jvmTarget
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    // Local Modules
    implementation(project(":data"))
    implementation(project(":domain"))

    // Core
    implementation(Libs.core_ktx)
    implementation(Libs.app_compat)
    implementation(Libs.material)
    implementation(Libs.constraint_layout)
    implementation("androidx.legacy:legacy-support-v4:1.0.0")

    // Test
    testImplementation(TestLibs.junit)
    testImplementation(TestLibs.coroutines)
    testImplementation(TestLibs.truth)
    testImplementation(TestLibs.mockk)
    testImplementation(TestLibs.mockk_jvm)

    // Android Test
    androidTestImplementation(AndroidTestLibs.junit)
    androidTestImplementation(AndroidTestLibs.truth)
    androidTestImplementation(AndroidTestLibs.espresso)
    androidTestImplementation(AndroidTestLibs.arch_core)
    androidTestImplementation(AndroidTestLibs.coroutines)
    androidTestImplementation(AndroidTestLibs.mockk)
    androidTestImplementation(AndroidTestLibs.mockk_jvm)

    // Splash Screen
    implementation(Libs.splashscreen)

    // Lifecycle
    implementation(Libs.lifecycle_runtime)
    implementation(Libs.viewModel)

    // Coroutine
    implementation(Libs.coroutines_core)
    implementation(Libs.coroutines_android)

    // Navigation
    implementation(Libs.navigation)
    implementation(Libs.navigation_fragment)

    // Room
    implementation(Libs.room_runtime)
    implementation(Libs.room_ktx)
    ksp(Libs.room_compiler_ksp)

    // Hilt
    implementation(Libs.hilt_android)
    kapt(Libs.hilt_compiler_dagger_kapt)
    implementation(Libs.hilt_work)
    kapt(Libs.hilt_compiler_android_kapt)

    // WorkManager
    implementation(Libs.work_manager)

    // Zxing Barcode
    implementation(Libs.zxing)
}