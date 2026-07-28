package com.cinetrack.app.presentation.detail

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.cinetrack.app.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val viewModel : DetailViewModel by viewModels ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val tvMessage = findViewById<TextView>(R.id.tvDetailMessage)
        val groupContent = findViewById<View>(R.id.groupDetailContent)
        val tvTitle = findViewById<TextView>(R.id.tvDetailTitle)
        val tvSubtitle = findViewById<TextView>(R.id.tvDetailSubtitle)
        val tvGenres = findViewById<TextView>(R.id.tvDetailGenres)
        val tvOverview = findViewById<TextView>(R.id.tvDetailOverview)
        val tvCast = findViewById<TextView>(R.id.tvDetailCast)

        lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                when (state) {
                    is DetailUiState.Loading -> {
                        tvMessage.visibility = View.VISIBLE
                        tvMessage.text = "Memuat..."
                        groupContent.visibility = View.GONE
                    }
                    is DetailUiState.Error -> {
                        tvMessage.visibility = View.VISIBLE
                        groupContent.visibility = View.GONE
                        tvMessage.text = state.message
                    }
                    is DetailUiState.Success -> {
                        tvMessage.visibility = View.GONE
                        groupContent.visibility = View.VISIBLE
                        tvTitle.text = state.detail.title
                        tvSubtitle.text = "${state.detail.releaseYear} • ${state.detail.ratingLabel} • ${state.detail.runtimeLabel}"
                        tvGenres.text = state.detail.genres.joinToString(", ")
                        tvOverview.text = state.detail.overview
                        tvCast.text = state.detail.castNames.joinToString(", ")
                    }
                }
            }
        }

    }

}