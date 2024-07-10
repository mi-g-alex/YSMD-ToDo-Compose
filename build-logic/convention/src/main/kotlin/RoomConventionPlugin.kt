import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class RoomConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            with(pluginManager) {
                apply("com.google.devtools.ksp")
            }

            dependencies {
                add("ksp", versionCatalog().findLibrary("androidx.room.compiler").get())
                add("implementation", versionCatalog().findLibrary("androidx.room.ktx").get())
                add("implementation", versionCatalog().findLibrary("androidx.room.runtime").get())
            }
        }
    }
}