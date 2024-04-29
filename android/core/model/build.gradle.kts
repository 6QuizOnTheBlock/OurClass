plugins {
    alias(libs.plugins.sixkids.java.library)
    alias(libs.plugins.kotlin.serialization)
}

dependencies{
    implementation(libs.kotlinx.serialization.json)
}
