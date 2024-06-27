package by.g_alex.ysmd_todo_compose.di

import by.g_alex.ysmd_todo_compose.data.repository.ToDoRepositoryImpl
import by.g_alex.ysmd_todo_compose.domain.ToDoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideToDoRepository(): ToDoRepository {
        return ToDoRepositoryImpl()
    }

}