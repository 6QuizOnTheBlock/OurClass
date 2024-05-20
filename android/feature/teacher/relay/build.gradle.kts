plugins {
    alias(libs.plugins.sixkids.android.feature.compose)
}

android {
    namespace = "com.sixkids.teacher.relay"
}

dependencies {
    implementation(libs.bundles.paging)

    implementation(libs.kotlinx.serialization.json)

    implementation(projects.core.nfc)
}
