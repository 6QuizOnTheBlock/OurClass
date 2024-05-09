plugins {
    alias(libs.plugins.sixkids.android.library)
}

android {
    namespace = "com.sixkids.core.bluetooth"

}

dependencies {
    implementation(libs.androidx.annotation.jvm)
}
