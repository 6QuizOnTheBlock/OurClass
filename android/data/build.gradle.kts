plugins {
    alias(libs.plugins.sixkids.android.library)
}

android {
    namespace = "com.sixkids.data"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.domain)

    testImplementation(libs.junit)
}
