plugins {
    alias(libs.plugins.sixkids.android.application)
}

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
}
