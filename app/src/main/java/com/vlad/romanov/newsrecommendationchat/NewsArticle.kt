package com.vlad.romanov.newsrecommendationchat

data class NewsArticle(
    val distance: Double,
    val domain: String,
    val link: String,
    val published: String,
    val summary: String,
    val title: String,
    val explanation: String
)
