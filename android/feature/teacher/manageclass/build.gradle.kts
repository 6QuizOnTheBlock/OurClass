plugins {
    alias(libs.plugins.sixkids.android.feature.compose)
}

android {
    namespace = "com.sixkids.teacher.manageclass"
}

dependencies {
    implementation(projects.core.designsystem)

    implementation(libs.bundles.paging)
}
