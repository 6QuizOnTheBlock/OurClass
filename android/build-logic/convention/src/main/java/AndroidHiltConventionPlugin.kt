import com.sixkids.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {

        with(target) {
            with(pluginManager) {
                apply("com.google.devtools.ksp")
                apply("dagger.hilt.android.plugin")
            }
            dependencies {
                "implementation"("com.google.dagger:hilt-android:${libs.findVersion("hilt").get()}")
                "ksp"("com.google.dagger:hilt-compiler:${libs.findVersion("hilt").get()}")
            }
        }
    }
}
