import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

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

    }
}

dependencies {
    implementation(projects.feature.navigator)

    implementation(libs.kakao.user)
}
