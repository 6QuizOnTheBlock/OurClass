import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.util.Properties

plugins {
    alias(libs.plugins.sixkids.android.application)
}

fun getProperty(propertyKey: String): String = gradleLocalProperties(rootDir, providers).getProperty(propertyKey)

android {
    namespace = "com.sixkids.ulban"
    defaultConfig {
        applicationId = "com.sixkids.ulban"
        versionCode = 1
        versionName = "1.0"

        val localProperties = Properties()
        val localPropertiesFile = rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            localProperties.load(localPropertiesFile.inputStream())
        }

        val nativeAppKey = localProperties.getProperty("kakao_app_key_cus") ?: ""
        manifestPlaceholders["NATIVE_APP_KEY"] = nativeAppKey

        buildConfigField("String", "KAKAO_NATIVE_APP_KEY", getProperty("KAKAO_APP_KEY"))
    }

    buildFeatures{
        buildConfig = true
    }
}

dependencies {
    implementation(projects.feature.navigator)

    implementation(libs.kakao.user)
}
