import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.util.Properties

plugins {
    alias(libs.plugins.sixkids.android.feature.compose)
}

fun getProperty(propertyKey: String): String =
    gradleLocalProperties(rootDir, providers).getProperty(propertyKey)

android {
    namespace = "com.sixkids.teacher.board"

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
}
