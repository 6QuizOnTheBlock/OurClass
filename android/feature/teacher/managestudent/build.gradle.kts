plugins {
    alias(libs.plugins.sixkids.android.feature.compose)
}

android {
    namespace = "com.sixkids.teacher.managestudent"
}

dependencies {
    implementation(projects.core.designsystem)
    implementation(libs.accompanist.pager)
}
