package by.g_alex.ysmd_todo_compose.presentation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavHostController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import by.g_alex.ysmd_todo_compose.data.repository.ToDoRepository
import by.g_alex.ysmd_todo_compose.presentation.todo.create_edit_todo.CreateEditToDoScreen
import by.g_alex.ysmd_todo_compose.presentation.todo.todo_list.ToDoListScreen

object Routes {
    const val MAIN_ROUTE = "MAIN_ROUTE"
    const val MAIN_SCREEN = "MAIN_SCREEN"
    const val EDIT_CREATE_SCREEN = "EDIT_CREATE_SCREEN/{id}"

}

@Composable
fun NavigationScreen(
    navController: NavHostController = rememberNavController()
) {

    val todoRep = ToDoRepository()

    val enter = slideInHorizontally(
        initialOffsetX = { 450 },
        animationSpec = tween(
            durationMillis = 250,
            easing = FastOutSlowInEasing
        )
    ) + fadeIn(animationSpec = tween(250))

    val out = slideOutHorizontally(
        targetOffsetX = { 450 },
        animationSpec = tween(
            durationMillis = 250,
            easing = FastOutSlowInEasing
        )
    ) + fadeOut(animationSpec = tween(250))


    NavHost(
        navController = navController,
        startDestination = Routes.MAIN_ROUTE
    ) {

        navigation(
            route = Routes.MAIN_ROUTE,
            startDestination = Routes.MAIN_SCREEN
        ) {
            composable(
                route = Routes.MAIN_SCREEN,
            ) { _ ->
                ToDoListScreen(todoRep) {
                    navController.navigate(Routes.EDIT_CREATE_SCREEN.replace("{id}", it.toString()))
                }
            }

            composable(
                route = Routes.EDIT_CREATE_SCREEN,
                enterTransition = { enter },
                exitTransition = { out }
            ) { entry ->
                val id = entry.arguments?.getString("id")
                CreateEditToDoScreen(id, todoRep) { navController.popBackStack() }
            }
        }
    }

}