plugins {
    alias(libs.plugins.sixkids.android.feature.compose)
    kotlin("plugin.serialization") version "1.9.24"
}

android {
    namespace = "com.sixkids.student.relay"
}

dependencies {
    implementation(libs.bundles.paging)

    implementation(libs.kotlinx.serialization.json)

    implementation(projects.core.nfc)
}
