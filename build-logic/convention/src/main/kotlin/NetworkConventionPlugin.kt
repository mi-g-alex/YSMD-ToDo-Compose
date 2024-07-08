import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class NetworkConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                add("implementation", versionCatalog().findLibrary("retrofit2.retrofit").get())
                add("implementation", versionCatalog().findLibrary("converter.gson").get())
                add("implementation", versionCatalog().findLibrary("okhttp.v500alpha14").get())
                add("implementation", versionCatalog().findLibrary("okhttp3.logging.interceptor").get())
                add("implementation", versionCatalog().findLibrary("androidx.work.runtime.ktx").get())
            }
        }
    }
}