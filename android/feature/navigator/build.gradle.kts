plugins {
    alias(libs.plugins.sixkids.android.feature.compose)
}

android {
    namespace = "com.sixkids.navigator"
}

dependencies {
    implementation(projects.feature.teacher.home)
    implementation(projects.feature.teacher.board)
    implementation(projects.feature.teacher.challenge)
}
