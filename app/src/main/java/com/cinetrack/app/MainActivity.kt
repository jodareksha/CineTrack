package com.cinetrack.app

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.cinetrack.app.presentation.trending.TrendingUiState
import com.cinetrack.app.presentation.trending.TrendingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: TrendingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvResult = findViewById<TextView>(R.id.tvResult)

        lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                when (state) {
                    is TrendingUiState.Loading -> {
                        tvResult.text = "Loading..."
                    }
                    is TrendingUiState.Success -> {
                        val dataTitle = state.movies.map{it.title}.joinToString("\n")
                        tvResult.text = dataTitle
                    }
                    is TrendingUiState.Error -> {
                        tvResult.text = state.message
                    }
                }
            }
            }
        }

}
