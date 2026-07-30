package com.cinetrack.app.baselineprofile

import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test

class StartupBaselineProfile {

    @get:Rule
    val baselineProfileRule = BaselineProfileRule()

    @Test
    fun startup() = baselineProfileRule.collect(
        packageName = "com.cinetrack.app"
    ) {
        pressHome()
        startActivityAndWait()

        device.wait(Until.hasObject(By.textContains("2026")), 5_000)
    }
}