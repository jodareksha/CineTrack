package com.cinetrack.app

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.cinetrack.app.kata.CoroutineKata.safeApiCall
import com.cinetrack.app.kata.Result
import com.cinetrack.app.network.NetworkModule
import com.cinetrack.app.network.dto.TrendingResponseDto
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvResult = findViewById<TextView>(R.id.tvResult)
        lifecycleScope.launch {
            val result: Result<TrendingResponseDto> = safeApiCall {
                NetworkModule.tmdbApi.getTrendingMovies()
            }
            when (result) {
                is Result.Success -> {
                    val movieTitles = result.data.results.map { it.title }.joinToString("\n")
                    tvResult.text = movieTitles
                }
                is Result.Error -> {
                    tvResult.text = result.message
                }
                is Result.Loading -> {
                    tvResult.text = "Loading..."
                }
            }
        }
    }
}
