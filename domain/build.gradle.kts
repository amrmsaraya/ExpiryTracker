plugins {
    id("java-library")
    id("kotlin")
}

java {
    sourceCompatibility = AndroidConfig.sourceCompatibility
    targetCompatibility = AndroidConfig.targetCompatibility
}

dependencies {

    // Libs
    implementation(Libs.coroutines_core)

    // Test
    testImplementation(TestLibs.junit)
    testImplementation(TestLibs.coroutines)
    testImplementation(TestLibs.truth)
    testImplementation(TestLibs.mockk)
    testImplementation(TestLibs.mockk_jvm)
}