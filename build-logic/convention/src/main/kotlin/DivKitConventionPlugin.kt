import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class DivKitConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            dependencies {
                add("implementation", versionCatalog().findLibrary("div").get())
                add("implementation", versionCatalog().findLibrary("div.core").get())
                add("implementation", versionCatalog().findLibrary("div.json").get())
                add("implementation", versionCatalog().findLibrary("div.rive").get())
                add("implementation", versionCatalog().findLibrary("div.picasso").get())
                add("implementation", versionCatalog().findLibrary("div.utils").get())
                add("implementation", versionCatalog().findLibrary("div.zoom").get())
            }
        }
    }
}