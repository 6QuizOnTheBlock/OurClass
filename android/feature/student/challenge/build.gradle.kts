import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.util.Properties

plugins {
    alias(libs.plugins.sixkids.android.feature.compose)
}

fun getProperty(propertyKey: String): String =
    gradleLocalProperties(rootDir, providers).getProperty(propertyKey)

android {
    namespace = "com.sixkids.student.challenge"
    defaultConfig {
        val localProperties = Properties()
        val localPropertiesFile = rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            localProperties.load(localPropertiesFile.inputStream())
        }

        val sseUrl = localProperties.getProperty("SSE_ENDPOINT") ?: ""

        buildConfigField("String", "SSE_ENDPOINT", "\"${sseUrl}\"")
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.bundles.paging)
    implementation(projects.core.bluetooth)
    implementation(libs.okhttp.eventsource)
    implementation(libs.okhttp.sse)
    implementation(libs.okhttp.logginginterceptor)
}
