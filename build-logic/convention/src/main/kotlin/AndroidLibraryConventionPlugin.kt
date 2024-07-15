import com.android.build.gradle.AppExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("kotlin-android")
            }

            val android = target.extensions.getByName("android") as AppExtension

            android.apply {

                compileSdkVersion(Versions.compileSdk)

                defaultConfig {
                    applicationId = "by.g_alex.ysmd_todo_compose"

                    minSdk = Versions.minSdk
                    targetSdk = Versions.targetSdk

                    versionCode = Versions.versionCode
                    versionName = Versions.versionName

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

                    vectorDrawables.apply {
                        useSupportLibrary = true
                    }
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_18
                    targetCompatibility = JavaVersion.VERSION_18
                }

                findProperty("kotlinOptions")

                buildTypes {
                    getByName("release") {
                        isMinifyEnabled = false
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }
                }

                packagingOptions.apply {
                    resources {
                        excludes += "/META-INF/{AL2.0,LGPL2.1}"
                    }
                }

            }
        }
    }
}