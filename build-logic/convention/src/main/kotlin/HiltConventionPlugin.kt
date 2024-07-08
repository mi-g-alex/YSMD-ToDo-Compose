import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class HiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("kotlin-kapt")
                apply("com.google.dagger.hilt.android")
            }

            dependencies {
                add("implementation", versionCatalog().findLibrary("hilt.android").get())
                add("implementation", versionCatalog().findLibrary("androidx.hilt.work").get())
                add("implementation", versionCatalog().findLibrary("androidx.hilt.navigation.compose").get())
                add("kapt", versionCatalog().findLibrary("hilt.android.compiler").get())
                add("kapt", versionCatalog().findLibrary("androidx.hilt.compiler").get())
            }
        }
    }
}