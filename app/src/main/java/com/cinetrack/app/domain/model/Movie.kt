package com.cinetrack.app.domain.model

/**
 * Ini DOMAIN MODEL — representasi film yang dipakai oleh UI dan business logic kita.
 * Perhatikan: TIDAK ADA import dari package `network.dto` di sini. Domain model tidak
 * boleh tahu-menahu soal bagaimana data itu didapat (network/JSON), supaya:
 *
 * 1. Kalau nanti TMDB ubah struktur JSON-nya, atau kita ganti sumber data
 *    (misal tambah data dari Room cache), domain model dan UI tidak perlu berubah
 *    sama sekali — cukup mapper-nya yang disesuaikan.
 * 2. Testing jadi gampang: bikin Movie palsu untuk test tidak perlu bikin MovieDto
 *    palsu dengan struktur JSON yang ribet.
 *
 * Ini prinsip inti Clean Architecture: domain layer adalah "pusat", tidak bergantung
 * ke layer luar (data/presentation) — layer luar yang bergantung ke domain.
 */
data class Movie(
    val id: Int,
    val title: String,
    val posterUrl: String?,
    val ratingLabel: String,
    val releaseYear: String,
    val overview: String
)
