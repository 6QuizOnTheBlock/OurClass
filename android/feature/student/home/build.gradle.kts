import java.util.Properties

plugins {
    alias(libs.plugins.sixkids.android.feature.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.sixkids.student.home"

    defaultConfig {
        val localProperties = Properties()
        val localPropertiesFile = rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            localProperties.load(localPropertiesFile.inputStream())
        }

        val stompUrl = localProperties.getProperty("STOMP_ENDPOINT") ?: ""

        buildConfigField("String", "STOMP_ENDPOINT", "\"${stompUrl}\"")
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.okhttp)
    implementation(libs.okhttp.logginginterceptor)

    implementation(libs.moshi.kotlin)
    implementation(libs.moshi.converter)
    implementation(libs.krossbow.stomp. core)
    implementation(libs.krossbow.websocket.okhttp)
    implementation(libs.krossbow.stomp.moshi)

    implementation(libs.bundles.paging)
    
    implementation(libs.kotlinx.serialization.json)
    implementation(projects.core.nfc)

}
