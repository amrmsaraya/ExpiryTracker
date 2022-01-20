plugins {
    id("java-library")
    id("kotlin")
}

java {
    sourceCompatibility = AndroidConfig.sourceCompatibility
    targetCompatibility = AndroidConfig.targetCompatibility
}

dependencies {
    implementation(Libs.coroutines_core)
}