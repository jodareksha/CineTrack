package com.cinetrack.app.presentation.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cinetrack.app.R
import com.cinetrack.app.domain.model.Movie

/**
 * Ini pertama kalinya kita pakai RecyclerView di project ini — sebelumnya
 * (MainActivity Phase 1) daftar film cuma digabung jadi 1 string panjang di
 * TextView, yang tidak scalable untuk list panjang (bayangkan render 10.000 baris
 * teks sekaligus) dan tidak bisa di-klik per item.
 *
 * KONSEP INTI — kenapa RecyclerView butuh "Adapter" + "ViewHolder", bukan cuma
 * loop biasa bikin View satu-satu:
 *
 * Kalau list punya 500 film tapi layar cuma muat 8 item kelihatan, RecyclerView
 * TIDAK bikin 500 View. Dia cuma bikin ~10 View (8 kelihatan + sedikit buffer),
 * lalu waktu kamu scroll, View yang keluar dari layar di-RECYCLE (dipakai ulang)
 * untuk menampilkan data item berikutnya yang baru masuk ke layar — bukan dibuang
 * dan dibikin View baru. Ini yang bikin scroll list panjang tetap smooth/performant.
 *
 * - "ViewHolder" = pemegang referensi ke View yang di-recycle itu (supaya tidak
 *   perlu findViewById() berulang-ulang tiap kali View dipakai ulang — itu mahal)
 * - "Adapter" = penghubung antara data (List<Movie>) dengan ViewHolder yang
 *   direcycle itu
 *
 * Method-method wajib yang RecyclerView.Adapter minta kita implement:
 * - onCreateViewHolder: dipanggil waktu RecyclerView butuh ViewHolder BARU (jarang
 *   dipanggil setelah scroll beberapa layar, karena kebanyakan sudah di-recycle)
 * - onBindViewHolder: dipanggil TIAP KALI sebuah ViewHolder (baru atau hasil
 *   recycle) perlu diisi data terbaru — ini yang sering dipanggil berkali-kali
 * - getItemCount: RecyclerView tanya "total ada berapa item di data kamu?"
 */
class MovieAdapter(
    private val onMovieClick: (Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var movies: List<Movie> = emptyList()


    fun submitList(newMovies: List<Movie>) {
        movies = newMovies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvMovieTitle)
        private val tvSubtitle = itemView.findViewById<TextView>(R.id.tvMovieSubtitle)

        fun bind(movie: Movie) {
            tvTitle.text = movie.title
            tvSubtitle.text = "${movie.releaseYear} \u2022 ${movie.ratingLabel}"
            itemView.setOnClickListener { onMovieClick(movie) }
        }
    }
}