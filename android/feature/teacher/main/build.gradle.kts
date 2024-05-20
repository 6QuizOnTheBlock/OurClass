plugins {
    alias(libs.plugins.sixkids.android.feature.compose)
}

android {
    namespace = "com.sixkids.teacher.main"
}

dependencies {
    implementation(libs.accompanist.pager)
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)
}
