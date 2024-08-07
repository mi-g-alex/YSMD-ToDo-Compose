package by.g_alex.ysmd_todo_compose.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import by.g_alex.ysmd_todo_compose.common.Constants
import by.g_alex.ysmd_todo_compose.common.network.getNetworkConnectionState
import by.g_alex.ysmd_todo_compose.common.network.networkCallback
import by.g_alex.ysmd_todo_compose.data.remote.ToDoAPI
import by.g_alex.ysmd_todo_compose.data.repository.ToDoApiRepositoryImpl
import by.g_alex.ysmd_todo_compose.domain.repository.ToDoApiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
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
class NetworkModule {

    @Provides
    @Singleton
    @Inject
    fun provideToDoAPI(prefs: AppModule.MyPreference): ToDoAPI {

        val loggerInspector = HttpLoggingInterceptor()
        loggerInspector.level = HttpLoggingInterceptor.Level.BODY

        val clint = OkHttpClient.Builder()
            .addInterceptor(Authentication(prefs))
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
    fun provideToDOAPIRepository(api: ToDoAPI): ToDoApiRepository = ToDoApiRepositoryImpl(api)

    class Authentication(private val prefs: AppModule.MyPreference) : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {

            val token = prefs.getToken()
            val isBear = prefs.getAuthType()

            val revision = prefs.getRevision()

            val apiKey = when (isBear) {
                true -> "Bearer $token"

                false -> "OAuth $token"

                null -> ""
            }

            val request = chain.request().newBuilder()
                .addHeader("Authorization", apiKey)
                .addHeader("X-Last-Known-Revision", revision.toString())
                .build()

            return chain.proceed(request)
        }
    }

    @Singleton
    class NetworkState @Inject constructor(@ApplicationContext private val context: Context) {

        fun getConnectivityAsFlow(): Flow<Boolean> = callbackFlow {
            val callback = networkCallback { connectionState ->
                trySend(connectionState)
            }

            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val networkRequest = NetworkRequest
                .Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build()

            connectivityManager.registerNetworkCallback(networkRequest, callback)
            val currentState = getNetworkConnectionState(connectivityManager)
            trySend(currentState)

            awaitClose {
                connectivityManager.unregisterNetworkCallback(callback)
            }
        }
    }
}