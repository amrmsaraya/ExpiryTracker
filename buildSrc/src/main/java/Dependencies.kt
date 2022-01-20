import org.gradle.api.JavaVersion


object AndroidConfig {
    // Android SDK
    const val compileSdk = 31
    const val minSdk = 24
    const val targetSdk = compileSdk

    // Compatibility
    val sourceCompatibility = JavaVersion.VERSION_1_8
    val targetCompatibility = JavaVersion.VERSION_1_8

    // JvmTarget
    const val jvmTarget = "1.8"

    // Version
    const val versionCode = 1
    const val versionName = "1.0.0"
}

object Versions {

    // Kotlin
    const val kotlin = "1.6.10"

    // Libs
    const val lifecycle = "2.4.0"
    const val core_ktx = "1.7.0"
    const val app_compat = "1.4.1"
    const val material = "1.5.0"
    const val constraint_layout = "2.1.2"
    const val coroutines = "1.6.0-native-mt"
    const val navigation = "2.4.0-rc01"
    const val splashscreen = "1.0.0-beta01"
    const val room = "2.4.0"
    const val hilt = "2.40.5"
    const val work_manager = "2.7.1"
    const val zxing = "4.3.0"

    // Test
    const val junit = "4.13.2"
    const val junit_android = "1.1.3"
    const val truth = "1.1.3"
    const val espresso = "3.4.0"

}

object Libs {
    const val core_ktx = "androidx.core:core-ktx:${Versions.core_ktx}"
    const val app_compat = "androidx.appcompat:appcompat:${Versions.app_compat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraint_layout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout}"

    // Splash Screen
    const val splashscreen = "androidx.core:core-splashscreen:${Versions.splashscreen}"

    // Lifecycle
    const val lifecycle_runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"

    // Coroutine
    const val coroutines_core =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutines_android =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    // Room
    const val room_runtime = "androidx.room:room-runtime:${Versions.room}"
    const val room_ktx = "androidx.room:room-ktx:${Versions.room}"
    const val room_compiler_ksp = "androidx.room:room-compiler:${Versions.room}"

    // Hilt
    const val hilt_android = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hilt_compiler_dagger_kapt = "com.google.dagger:hilt-compiler:${Versions.hilt}"
    const val hilt_compose_navigation = "androidx.hilt:hilt-navigation-compose:1.0.0-rc01"
    const val hilt_compiler_android_kapt = "androidx.hilt:hilt-compiler:1.0.0"

    // Navigation
    const val navigation = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigation_fragment = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"

    // WorkManger
    const val work_manager = "androidx.work:work-runtime-ktx:${Versions.work_manager}"

    // Zxing Barcode
    const val zxing = "com.journeyapps:zxing-android-embedded:${Versions.zxing}"

}

object TestLibs {
    const val junit = "junit:junit:${Versions.junit}"
    const val coroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    const val truth = "com.google.truth:truth:${Versions.truth}"
}

object AndroidTestLibs {
    const val junit = "androidx.test.ext:junit:${Versions.junit_android}"
    const val truth = "com.google.truth:truth:${Versions.truth}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
}