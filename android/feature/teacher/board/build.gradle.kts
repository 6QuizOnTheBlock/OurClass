plugins {
    alias(libs.plugins.sixkids.android.feature.compose)
}

android {
    namespace = "com.sixkids.teacher.board"
}

dependencies {
    implementation(libs.okhttp)
    implementation(libs.bundles.stomp)
}
