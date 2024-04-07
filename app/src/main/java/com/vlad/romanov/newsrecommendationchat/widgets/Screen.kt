package com.vlad.romanov.newsrecommendationchat.widgets

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Feed : Screen("feed", "Feed", Icons.Filled.List)
    object Chat : Screen("chat", "Chat", Icons.Filled.List)
}

