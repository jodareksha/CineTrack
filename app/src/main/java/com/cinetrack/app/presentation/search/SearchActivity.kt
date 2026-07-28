package com.cinetrack.app.presentation.search

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cinetrack.app.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.cinetrack.app.presentation.detail.DetailActivity
@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val etSearchQuery = findViewById<android.widget.EditText>(R.id.etSearchQuery)
        val tvSearchMessage = findViewById<TextView>(R.id.tvSearchMessage)
        val rvSearchResults = findViewById<RecyclerView>(R.id.rvSearchResults)

        val adapter = MovieAdapter(onMovieClick = { movie ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("movieId", movie.id)
            startActivity(intent)
        })
        rvSearchResults.layoutManager = LinearLayoutManager(this)
        rvSearchResults.adapter = adapter

        etSearchQuery.addTextChangedListener { text ->
            viewModel.onQueryChanged(text.toString())
        }
        lifecycleScope.launch {
           viewModel.uiState.collectLatest { state ->
               when (state){
                   is SearchUiState.Idle -> {
                       tvSearchMessage.visibility = View.VISIBLE
                       tvSearchMessage.text = "Ketik untuk mencari film"
                       rvSearchResults.visibility = View.GONE
                   }
                   is SearchUiState.Loading -> {
                       tvSearchMessage.visibility = View.VISIBLE
                       tvSearchMessage.text = "Mencari..."
                       rvSearchResults.visibility = View.GONE
                   }
                   is SearchUiState.Success -> {
                       tvSearchMessage.visibility = View.GONE
                       rvSearchResults.visibility = View.VISIBLE
                       adapter.submitList(state.movies)
                   }
                   is SearchUiState.Empty -> {
                       tvSearchMessage.visibility = View.VISIBLE
                       tvSearchMessage.text = "Tidak ditemukan"
                       rvSearchResults.visibility = View.GONE
                   }

                   is SearchUiState.Error ->{
                       tvSearchMessage.visibility = View.VISIBLE
                       tvSearchMessage.text = state.message
                       rvSearchResults.visibility = View.GONE
                   }
               }
           }
        }

    }
}