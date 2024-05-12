plugins {
    alias(libs.plugins.sixkids.android.library)
    alias(libs.plugins.sixkids.android.hilt)
}

android {
    namespace = "com.sixkids.data"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.domain)

    implementation(libs.bundles.retrofit)
    implementation(libs.okhttp.eventsource)
    implementation(libs.datastore)
    implementation(libs.paging)

    testImplementation(libs.junit)
}
