# Phase 1 — MVVM + Clean Architecture + Hilt

## Tujuan Belajar

Di Phase 0, `MainActivity` tahu segalanya: dia manggil Retrofit langsung, dia yang urus
error handling, dia yang urus DI (manual). Itu OK untuk smoke test, tapi tidak scalable
begitu app punya banyak layar dan fitur.

Phase 1 mecah tanggung jawab itu jadi 3 layer:

```
presentation (ViewModel, UI State)   <- yang di-observe UI
        |  bergantung ke
domain (Movie model, Repository interface)   <- "pusat", tidak bergantung ke apapun
        ^  di-implement oleh
data (RepositoryImpl, mapper, network)
```

Panah dependency-nya **searah ke domain**. Ini yang membuat domain layer bisa dites
tanpa network sama sekali, dan kalau nanti sumber data berubah (misal tambah Room cache),
domain & presentation tidak perlu tahu.

## Urutan pengerjaan (WAJIB urut, karena saling bergantung)

1. **`data/mapper/MovieMapper.kt`** — isi `TODO()` di `MovieDto.toDomain()`.
   Ini paling gampang dan langsung memakai kata #4 kamu (`toRatingLabel()`, `toReleaseYear()`).

2. **`data/repository/MovieRepositoryImpl.kt`** — isi `TODO()` di `getTrendingMovies()`.
   Menggabungkan `safeApiCall` + `.map` + hasil dari langkah 1.

3. **`presentation/trending/TrendingViewModel.kt`** — isi `TODO()` di `loadTrendingMovies()`.
   Setelah ini selesai, **run `TrendingViewModelTest`** (klik ikon ▶️ di sebelah nama class,
   sama seperti kata-kata Phase 0) — harus 3 test hijau sebelum lanjut.

4. **`MainActivity.kt`** — isi `TODO()` untuk collect `viewModel.uiState`.

5. **Build & run app di emulator/device.** Harus tetap menampilkan trending movies
   seperti Phase 0 — bedanya sekarang semua logic sudah dipisah rapi per layer.

## Kenapa test ViewModel duluan sebelum run app?

Ini kebiasaan yang membedakan senior: **verifikasi logic lewat unit test dulu**
(detik, tanpa emulator) sebelum buang waktu build+run app (menit, butuh emulator) untuk
ngecek hal yang sebenarnya bisa dicek lebih cepat. `TrendingViewModelTest` pakai
`FakeMovieRepository`, jadi tidak butuh network/emulator sama sekali untuk membuktikan
ViewModel kamu benar.

## Yang perlu kamu ketahui soal Hilt sebelum mulai

Kamu tidak perlu isi TODO apapun soal Hilt-nya sendiri (`di/NetworkModule.kt` dan
`di/RepositoryModule.kt` sudah saya isi lengkap dengan komentar penjelasan) — fokus
belajar Phase 1 ada di logic repository/mapper/viewmodel, bukan hafalan syntax Hilt.
Tapi **baca** komentar di kedua file itu, karena ini sering ditanyakan waktu interview:
bedanya `@Provides` vs `@Binds`, dan apa itu `@Singleton` + `@InstallIn(SingletonComponent::class)`.

## Checklist Phase 1

- [ ] `MovieMapper.kt` — `toDomain()` selesai
- [ ] `MovieRepositoryImpl.kt` — `getTrendingMovies()` selesai
- [ ] `TrendingViewModel.kt` — `loadTrendingMovies()` selesai, `TrendingViewModelTest`
      3 test hijau
- [ ] `MainActivity.kt` — observasi `uiState` selesai
- [ ] App jalan di emulator, tampilan sama seperti Phase 0 (tapi arsitektur beda total)
- [ ] Kamu bisa jelaskan: kenapa domain layer tidak boleh import dari package `network`?
- [ ] Kamu bisa jelaskan: bedanya `Result<T>` (di core/) dengan `TrendingUiState`
      (di presentation/) — kenapa dua-duanya perlu ada, tidak cukup satu saja?
- [ ] Commit ke GitHub dengan message yang jelas, misal:
      `git commit -m "Phase 1: MVVM + Clean Architecture + Hilt DI"`

## Setelah ini

Chat saya lagi dengan progress kamu. Kalau semua checklist di atas selesai, kita lanjut
ke **fitur baru** di atas fondasi ini — search movie + detail screen, sekalian latihan
`Result.map()` yang di kata #2 dulu masih abstrak, sekarang benar-benar dipakai buat
transform data API ke domain model.

Modularisasi Gradle beneran (`:core:network`, `:feature:trending`, dst) akan kita lakukan
**setelah** fitur search & detail selesai — supaya kamu sudah punya 2+ fitur nyata untuk
dipakai latihan menentukan batas modul yang masuk akal (modularisasi 1 fitur itu
percuma, baru kerasa manfaatnya waktu ada beberapa fitur yang perlu dipisah).
