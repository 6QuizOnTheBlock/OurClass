plugins {
    alias(libs.plugins.sixkids.android.library)
    alias(libs.plugins.sixkids.android.hilt)
}

android {
    namespace = "com.sixkids.core.bluetooth"

}

dependencies {
    implementation(libs.androidx.annotation.jvm)
    implementation(libs.kotlinx.coroutines.core)
    implementation(projects.core.model)
}
