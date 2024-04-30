plugins {
    alias(libs.plugins.sixkids.android.feature.compose)
}

android {
    namespace = "com.sixkids.teacher.challenge"
}

dependencies {
    implementation(libs.coil.compose)
}
