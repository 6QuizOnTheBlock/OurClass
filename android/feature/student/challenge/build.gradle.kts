plugins {
    alias(libs.plugins.sixkids.android.feature.compose)
}

android {
    namespace = "com.sixkids.student.challenge"
}

dependencies {
    implementation(libs.bundles.paging)
    implementation(projects.core.bluetooth)
    implementation(libs.okhttp.eventsource)
}
