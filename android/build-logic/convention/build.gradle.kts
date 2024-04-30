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
    compileOnly(libs.ksp.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidApplication"){
            id="sixkids.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary"){
            id="sixkids.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose"){
            id="sixkids.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidHilt"){
            id="sixkids.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("javaLibrary"){
            id="sixkids.java.library"
            implementationClass = "JavaLibraryConventionPlugin"
        }
        register("featureCompose"){
            id="sixkids.android.feature.compose"
            implementationClass = "FeatureComposeConventionPlugin"
        }

    }
}
