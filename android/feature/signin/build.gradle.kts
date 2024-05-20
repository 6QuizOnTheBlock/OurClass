plugins {
    alias(libs.plugins.sixkids.android.feature.compose)
}

android {
    namespace = "com.sixkids.signin"
}

dependencies {
    implementation(libs.kakao.user)

}
