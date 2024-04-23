plugins {
    `kotlin-dsl`
}

group = "com.sixkids.ulban.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies{
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidApplication"){
            id="sixkids.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
    }
}
