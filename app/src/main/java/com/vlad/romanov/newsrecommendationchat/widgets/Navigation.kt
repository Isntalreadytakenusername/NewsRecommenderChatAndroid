package com.vlad.romanov.newsrecommendationchat.widgets

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vlad.romanov.newsrecommendationchat.RecommendationScreen
import com.vlad.romanov.newsrecommendationchat.SingleViewModel
import com.vlad.romanov.newsrecommendationchat.widgets.chat.ChatScreen



@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(Screen.Feed, Screen.Chat)
    BottomNavigation {
        val currentRoute = currentRoute(navController)
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(screen.label) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        // Avoid multiple copies of the same destination when reselecting the same item
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination
                        launchSingleTop = true
                        // Restore state when navigating to the screen
                        restoreState = true
                    }
                }
            )
        }
    }
}

// Helper function to determine the current route
@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var chatViewModel = SingleViewModel()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Feed.route,
            modifier = Modifier.padding(innerPadding) // Apply the inner padding here
        ) {
            composable(Screen.Feed.route) { RecommendationScreen() }
            composable(Screen.Chat.route) { ChatScreen(chatViewModel) }
        }
    }
}


//@Composable
//fun ChatScreen() {
//    Text("Chat Screen")
//}