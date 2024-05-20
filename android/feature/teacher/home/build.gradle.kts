plugins {
    alias(libs.plugins.sixkids.android.feature.compose)
}

android {
    namespace = "com.sixkids.teacher.home"
}

dependencies {
    implementation(projects.core.designsystem)
    implementation(projects.feature.teacher.challenge)
}
