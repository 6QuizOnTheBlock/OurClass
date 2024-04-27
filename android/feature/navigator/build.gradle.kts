plugins {
    alias(libs.plugins.sixkids.android.feature.compose)
}

android {
    namespace = "com.sixkids.navigator"
}

dependencies {
    implementation(projects.feature.home)
}
