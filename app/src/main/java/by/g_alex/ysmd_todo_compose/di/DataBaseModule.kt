package by.g_alex.ysmd_todo_compose.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import by.g_alex.ysmd_todo_compose.common.Constants
import by.g_alex.ysmd_todo_compose.data.local.ToDoDataBase
import by.g_alex.ysmd_todo_compose.data.remote.ToDoAPI
import by.g_alex.ysmd_todo_compose.data.repository.ToDoApiRepositoryImpl
import by.g_alex.ysmd_todo_compose.data.repository.ToDoDataBaseRepositoryImpl
import by.g_alex.ysmd_todo_compose.domain.repository.ToDoApiRepository
import by.g_alex.ysmd_todo_compose.domain.repository.ToDoDataBaseRepository
import com.google.gson.Gson
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
class DataBaseModule {

    @Provides
    @Singleton
    fun provideToDoDatabase(app: Application): ToDoDataBase =
        Room.databaseBuilder(
            app,
            ToDoDataBase::class.java,
            ToDoDataBase.DATABASE_NAME
        )
            .build()

    @Provides
    @Singleton
    fun provideToDoDataBaseRepository(db: ToDoDataBase): ToDoDataBaseRepository =
        ToDoDataBaseRepositoryImpl(db.toDoDao)

}