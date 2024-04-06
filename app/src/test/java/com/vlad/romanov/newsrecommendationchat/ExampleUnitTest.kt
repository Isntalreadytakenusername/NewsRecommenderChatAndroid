package com.vlad.romanov.newsrecommendationchat

import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test_api_call() {
        runBlocking {
            val rec_view_model = RecommendationViewModel()
            println(rec_view_model.getRecommendation().body()?.testingString)
        }
    }

    @Test
    fun test_api_call_articles() {
        runBlocking {
            val rec_view_model = RecommendationViewModel()
            println(rec_view_model.getRecommendedArticles().body()?.title)
        }
    }
}