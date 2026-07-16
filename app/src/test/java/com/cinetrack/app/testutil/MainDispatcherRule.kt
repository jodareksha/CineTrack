package com.cinetrack.app.testutil

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import kotlinx.coroutines.Dispatchers

/**
 * Kenapa ini dibutuhkan: `viewModelScope` di dalam ViewModel pakai Dispatchers.Main
 * secara default. Tapi di unit test (JVM biasa, bukan device/emulator), Dispatchers.Main
 * TIDAK tersedia — bakal crash "Module with the Main dispatcher had failed to
 * initialize" kalau tidak di-setup dulu.
 *
 * Rule ini mengganti Dispatchers.Main dengan test dispatcher sebelum tiap test jalan,
 * dan mengembalikannya lagi setelah selesai. Ini boilerplate STANDAR yang akan kamu
 * pakai di HAMPIR SEMUA test ViewModel ke depannya — cukup paham cara pakainya
 * (`@get:Rule val mainDispatcherRule = MainDispatcherRule()`), tidak perlu dihafal
 * detail implementasinya.
 *
 * UnconfinedTestDispatcher dipilih (bukan StandardTestDispatcher) supaya coroutine di
 * init{} ViewModel langsung jalan sinkron tanpa perlu advanceUntilIdle() manual — lebih
 * simpel untuk kasus kita sekarang. Di Phase 3 nanti kita akan bahas kapan perlu ganti
 * ke StandardTestDispatcher (untuk test yang butuh kontrol timing lebih presisi).
 */
@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {

    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}
