import com.android.build.api.dsl.ApplicationExtension
import com.sixkids.convention.Const
import com.sixkids.convention.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

@Suppress("unused")
internal class AndroidApplicationConventionPlugin : Plugin<Project>{
    override fun apply(target: Project) {
        with(target){
            with(pluginManager){
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("sixkids.android.hilt")
            }

            extensions.configure<ApplicationExtension>{
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = Const.TARGET_SDK
            }

        }
    }

}
