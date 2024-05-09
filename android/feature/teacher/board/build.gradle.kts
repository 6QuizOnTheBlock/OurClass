plugins {
    alias(libs.plugins.sixkids.android.feature.compose)
}

android {
    namespace = "com.sixkids.teacher.board"
}

dependencies {
    implementation(libs.okhttp)
    implementation(libs.okhttp.logginginterceptor)
//    implementation(libs.bundles.stomp)

    implementation(libs.moshi.kotlin)
    implementation(libs.moshi.converter)
    implementation("org.hildan.krossbow:krossbow-stomp-core:7.0.0")
    implementation("org.hildan.krossbow:krossbow-websocket-okhttp:7.0.0")
    implementation("org.hildan.krossbow:krossbow-stomp-moshi:7.0.0")
}
