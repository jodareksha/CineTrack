package com.cinetrack.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cinetrack.app.presentation.detail.DetailActivity
import com.cinetrack.app.presentation.search.SearchActivity
import com.cinetrack.app.presentation.trending.TrendingScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MainScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScreenScaffold(
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            Surface(
                shadowElevation = 8.dp,
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                TopAppBar(
                    title = {
                        Text(
                            text = "CineTrack",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                        )
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                context.startActivity(Intent(context, SearchActivity::class.java))
                            }
                        ) {
                            Icon(Icons.Default.Search, contentDescription = "Cari Film")
                        }
                    },

                )

            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            content()
        }
    }

}

@Composable
private fun MainScreen() {
    val context = LocalContext.current
    MainScreenScaffold {
        TrendingScreen(
            onMovieClick = { movie ->
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("movieId", movie.id)
                context.startActivity(intent)
            }
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
private fun MainScreenPreview() {
    MaterialTheme {
        MainScreenScaffold {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("(Trending content di sini)")
            }
        }
    }
}