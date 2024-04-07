package com.vlad.romanov.newsrecommendationchat.ui.theme

import androidx.compose.ui.graphics.Color

data class AppColorScheme(
    val background: Color,
    val itemTextBox: Color,
    val textNormal: Color,
    val textHighlight: Color
) {
    companion object {
        fun fromHex(
            background: String,
            itemTextBox: String,
            textNormal: String,
            textHighlight: String
        ) = AppColorScheme(
            background.toColor(),
            itemTextBox.toColor(),
            textNormal.toColor(),
            textHighlight.toColor()
        )

        private fun String.toColor(): Color {
            val colorInt = this.removePrefix("#").toLong(16).toInt()
            return Color(colorInt or 0xFF000000.toInt())
        }
    }
}
