import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class ComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            val android = target.extensions.getByName("android") as AppExtension

            android.apply {
                buildFeatures.apply {
                    compose = true
                }
            }

            dependencies {
                add("implementation", versionCatalog().findLibrary("androidx.activity.compose").get())
                add("implementation", platform(versionCatalog().findLibrary("androidx.compose.bom").get()))
                add("implementation", versionCatalog().findLibrary("androidx.ui").get())
                add("implementation", versionCatalog().findLibrary("androidx.ui.graphics").get())
                add("implementation", versionCatalog().findLibrary("androidx.ui.tooling.preview").get())
                add("implementation", versionCatalog().findLibrary("androidx.material3").get())
                add("implementation", versionCatalog().findLibrary("androidx.navigation.runtime.ktx").get())
                add("implementation", versionCatalog().findLibrary("androidx.navigation.compose").get())
            }
        }
    }
}