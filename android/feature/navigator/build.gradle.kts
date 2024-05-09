plugins {
    alias(libs.plugins.sixkids.android.feature.compose)
}

android {
    namespace = "com.sixkids.navigator"
}

dependencies {
    implementation(projects.feature.teacher.home)
    implementation(projects.feature.teacher.board)
    implementation(projects.feature.teacher.manageclass)
    implementation(projects.feature.teacher.challenge)
    implementation(projects.feature.signin)
    implementation(projects.feature.teacher.managestudent)
    implementation(projects.feature.teacher.main)
    implementation(projects.feature.student.board)
    implementation(projects.feature.student.home)
    implementation(projects.feature.student.challenge)
    implementation(projects.feature.student.relay)

    implementation(libs.permissions)
}
