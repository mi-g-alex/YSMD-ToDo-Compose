package by.g_alex.ysmd_todo_compose.di

import android.content.Context
import by.g_alex.ysmd_todo_compose.common.Constants
import by.g_alex.ysmd_todo_compose.data.remote.ToDoAPI
import by.g_alex.ysmd_todo_compose.data.repository.ToDoApiRepositoryImpl
import by.g_alex.ysmd_todo_compose.data.repository.ToDoRepositoryImpl
import by.g_alex.ysmd_todo_compose.domain.repository.ToDoApiRepository
import by.g_alex.ysmd_todo_compose.domain.repository.ToDoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideToDoRepository(): ToDoRepository {
        return ToDoRepositoryImpl()
    }

    @Provides
    @Singleton
    @Inject
    fun provideToDoAPI(@ApplicationContext context: Context): ToDoAPI {

        val loggerInspector = HttpLoggingInterceptor()
        loggerInspector.level = HttpLoggingInterceptor.Level.BODY

        val clint = OkHttpClient.Builder()
            .addInterceptor(Authentication(context))
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(loggerInspector)
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(clint)
            .build()
            .create(ToDoAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideIisAPIRepository(api: ToDoAPI): ToDoApiRepository = ToDoApiRepositoryImpl(api)

    class Authentication(context: Context) : Interceptor {
        private val prefs =
            context.getSharedPreferences(Constants.SHARED_PREF_TO_DO, Context.MODE_PRIVATE)

        override fun intercept(chain: Interceptor.Chain): Response {

            val bearToken = prefs.getString(Constants.SHARED_BEARER_TOKEN, null)
            val authToken = prefs.getString(Constants.SHARED_AUTH2_TOKEN, null)

            val revision = prefs.getInt(Constants.SHARED_REVISION, 0)

            val apiKey =
                if (bearToken != null) "Bearer $bearToken" else if (authToken != null) "OAuth $authToken" else ""

            val request = chain.request().newBuilder()
                .addHeader("Authorization", apiKey)
                .addHeader("X-Last-Known-Revision", revision.toString())
                .build()

            return chain.proceed(request)
        }
    }

    @Singleton
    class MyPreference @Inject constructor(@ApplicationContext context: Context) {
        private val prefs =
            context.getSharedPreferences(Constants.SHARED_PREF_TO_DO, Context.MODE_PRIVATE)

        fun setToken(token: String?, isBearer: Boolean?) {
            prefs.edit().remove(Constants.SHARED_REVISION).apply()
            if (token == null || isBearer == null) {
                prefs.edit()
                    .remove(Constants.SHARED_BEARER_TOKEN)
                    .remove(Constants.SHARED_AUTH2_TOKEN)
                    .apply()
            } else {
                if (isBearer == true) {
                    prefs.edit()
                        .putString(Constants.SHARED_BEARER_TOKEN, token)
                        .remove(Constants.SHARED_AUTH2_TOKEN)
                        .apply()
                } else {
                    prefs.edit()
                        .putString(Constants.SHARED_AUTH2_TOKEN, token)
                        .remove(Constants.SHARED_BEARER_TOKEN)
                        .apply()
                }
            }
        }

        /** Return
         *
         * True if auth use bearer
         *
         * False if auth use oauth2
         *
         * Null if not auth yet **/
        fun getAuthType(): Boolean? {
            val bearer = prefs.getString(Constants.SHARED_BEARER_TOKEN, null)
            val auth2 = prefs.getString(Constants.SHARED_AUTH2_TOKEN, null)

            if (bearer != null) return true
            if (auth2 != null) return false
            return null
        }

        /** Return token or empty string if not auth yet **/
        fun getToken(): String {
            val bearer = prefs.getString(Constants.SHARED_BEARER_TOKEN, "")
            val auth2 = prefs.getString(Constants.SHARED_AUTH2_TOKEN, "")
            return bearer + auth2
        }

        fun setRevision(rev: Int) {
            prefs.edit().putInt(Constants.SHARED_REVISION, rev).apply()
        }
    }


}