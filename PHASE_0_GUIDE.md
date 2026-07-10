# Phase 0 (Week 1) — Kotlin Idiom Audit

## Tujuan Belajar

Kamu sudah bisa Android (2 tahun Java/XML) dan sudah terbiasa dengan konsep async dari React Native. Phase 0 **bukan** belajar "apa itu Android" — ini audit spesifik ke 4 idiom Kotlin yang paling sering membedakan kode "Java yang ditulis pakai sintaks Kotlin" vs kode Kotlin yang idiomatik ala senior:

1. **Scope functions** (`let`, `apply`, `run`, `also`, `with`) — kapan pakai yang mana, bukan cuma tau syntax-nya
2. **Sealed class/interface** — modeling state (Loading/Success/Error) dengan exhaustive `when`
3. **Coroutines dasar** — `suspend fun`, `try/catch` di coroutine, `async/awaitAll` untuk parallel call
4. **Extension functions** — menambah fungsi ke tipe yang sudah ada tanpa inheritance

## Cara kerja belajar di project ini (PENTING)

Setiap file di `kata/` punya:
- Penjelasan konsep di komentar
- Function/class dengan `TODO("Implementasikan ini")`
- Test file terpisah di `src/test/.../kata/` yang **akan gagal** sampai kamu isi implementasinya

Ini pola **test-driven kata**: jalankan test dulu (akan merah/gagal), lalu isi kode di file `kata/` sampai test-nya hijau. Jangan lihat jawaban — kalau stuck lebih dari 30 menit di satu kata, baru chat saya untuk hint (bukan jawaban langsung).

Urutan pengerjaan:
1. `kata/01_ScopeFunctions.kt` → test: `ScopeFunctionsTest.kt`
2. `kata/02_SealedClasses.kt` → test: `SealedClassesTest.kt`
3. `kata/03_Coroutines.kt` → test: `CoroutinesTest.kt`
4. `kata/04_ExtensionFunctions.kt` → test: `ExtensionFunctionsTest.kt`

Jalankan test di Android Studio: klik kanan folder `src/test` → `Run 'Tests in kata'`, atau lewat terminal:
```
./gradlew testDebugUnitTest --tests "com.cinetrack.app.kata.*"
```

## Task network smoke-test (bukti environment jalan end-to-end)

File `TmdbApi.kt` sudah berisi 1 endpoint (`trending/movie/day`). File `NetworkModule.kt` sudah menyediakan instance Retrofit siap pakai (manual singleton — Hilt baru masuk di Phase 1, supaya kamu paham dulu "apa yang sebenarnya digantikan oleh DI framework" sebelum pakai Hilt).

Tugas kamu di `MainActivity.kt`:
- Ada `TODO` untuk memanggil `TmdbApi.getTrendingMovies()` di dalam `lifecycleScope.launch { ... }`
- Tangani hasilnya pakai sealed class `Result` yang kamu buat di kata #2
- Tampilkan judul-judul film ke `TextView` (boleh jelek, ini bukan tugas UI)

## Checklist Phase 0 (centang sebelum lanjut Phase 1)

- [ ] 4 file kata selesai, semua test hijau
- [ ] App berhasil compile & run di emulator/device
- [ ] MainActivity berhasil fetch trending movies dari TMDB dan menampilkan minimal judul film di layar
- [ ] Kamu bisa jelaskan (ke diri sendiri atau orang lain) kapan pakai `let` vs `apply` vs `also` — tanpa buka dokumentasi
- [ ] Kamu bisa jelaskan kenapa `sealed class` lebih aman daripada pakai `enum` + nullable data untuk modeling state
- [ ] Push project ini ke GitHub repo baru (public) — ini akan jadi portofolio kamu, mulai commit dari sekarang dengan message yang jelas per kata/fitur

## Setelah ini

Chat saya lagi dengan progress kamu (kata mana yang terasa sulit, error apa yang muncul, dsb). Saya akan review pendekatan kamu sebelum kita masuk **Phase 1: MVVM + Clean Architecture + Modularization**, di mana kita akan pecah project ini jadi multi-module dan menambah fitur search + detail screen.
