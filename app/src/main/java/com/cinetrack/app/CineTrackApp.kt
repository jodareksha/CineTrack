package com.cinetrack.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * @HiltAndroidApp memicu Hilt untuk generate dependency graph (component) di level
 * Application. Ini WAJIB ada di Application class, ini "pintu masuk" DI graph
 * yang dipakai semua @AndroidEntryPoint (Activity) dan @HiltViewModel di app ini.
 */
@HiltAndroidApp
class CineTrackApp : Application()
