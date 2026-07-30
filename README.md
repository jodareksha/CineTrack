# CineTrack 🎬 — Senior Android Learning Project

**CineTrack** adalah aplikasi discovery film & serial TV (data dari [TMDB API](https://www.themoviedb.org/documentation/api)) yang akan kita bangun **bertahap dari Phase 0 sampai Phase 5**, mengikuti roadmap Senior Android Engineer. Project ini akan menjadi portofolio akhir kamu.

## Kenapa CineTrack?

Dipilih secara spesifik karena satu domain (film/TV) ini natural memerlukan **semua skill senior** tanpa perlu ganti project:

| Kebutuhan fitur | Skill senior yang dilatih |
|---|---|
| List trending movies (network call) | Retrofit + Coroutines dasar |
| Search movies/TV | Debounce, Flow operators |
| Pagination (popular movies, banyak halaman) | Paging 3 |
| Detail film + cast + trailer | Nested API calls, parallel coroutines (`async/awaitAll`) |
| Simpan favorit offline | Room, offline-first architecture |
| Gambar poster/backdrop | Coil/Glide, image caching, performance |
| Migrasi UI XML → Compose | Transfer skill dari background XML kamu ke Compose |
| Modularisasi (`:core`, `:feature-movie`, `:feature-search`, dst) | Multi-module Gradle |
| Testing ViewModel & Repository | JUnit, MockK, Turbine |
| CI/CD | GitHub Actions |

Ini juga project yang **rekruter langsung paham** saat lihat portofolio kamu — bukan to-do list app generik.

## Referensi GitHub sejenis 

Jangan copy-paste — ini untuk dibaca **setelah** kamu mencoba sendiri, untuk membandingkan pendekatan:

- **pdalbem/movieDB** — TMDB + MVVM + LiveData/Flow/Paging3 + Room + Hilt + migrasi ke Compose (mirip roadmap kita persis)
- **raulbarca/tmdb-kotlin** — Clean Architecture murni, referensi bagus untuk Phase 1
- **Ajinkrishnak/TmdbAppCompose** — Compose + Hilt + Paging3, referensi untuk Phase 2
- Artikel: *"Android Architecture: MVVM with Coroutines + Retrofit + Hilt + Kotlin Flow + Room"* by Narendrasinh Dodiya (Medium) — penjelasan konsep yang selaras dengan struktur project ini
- GitHub topic browsing: [`tmdb-api` + Kotlin](https://github.com/topics/tmdb-api?l=kotlin) untuk melihat variasi pendekatan lain

## Setup awal (lakukan sebelum mulai coding)

1. Daftar akun gratis di https://www.themoviedb.org/ → Settings → API → request **API Key (v3 auth)**
2. Buat file `local.properties` di root project (sudah di-ignore git) dan tambahkan:
   ```
   TMDB_API_KEY=isi_api_key_kamu_disini
   ```
3. Buka project ini di Android Studio (versi stabil terbaru) → biarkan Gradle sync. Jika AGP/Kotlin version di `libs.versions.toml` sudah outdated, terima saran upgrade dari Android Studio.
4. Jalankan app di emulator/device — target di Phase 0 hanya: **app compile & jalan, menampilkan judul film trending di layar (versi jelek, belum ada styling)**.

---

