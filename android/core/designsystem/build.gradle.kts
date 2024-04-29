plugins {
    alias(libs.plugins.sixkids.android.library)
    alias(libs.plugins.sixkids.android.library.compose)
}

android {
    namespace = "com.sixkids.designsystem"
}

dependencies {
    implementation(projects.core.ui)
    implementation (libs.accompanist.systemuicontroller)
    implementation(libs.coil.compose)
}
