import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

class FeatureComposeConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("sixkids.android.library")
                apply("sixkids.android.library.compose")
                apply("sixkids.android.hilt")
            }

            dependencies {
                "implementation"(project(":core:model"))
                "implementation"(project(":core:ui"))
                "implementation"(project(":core:designsystem"))
                "implementation"(project(":domain"))
            }
        }
    }
}
