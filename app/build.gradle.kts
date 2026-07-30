import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.compose.compiler)
    id("jacoco")
}

// Baca TMDB_API_KEY dari local.properties (jangan pernah commit API key ke git)
val localProperties = Properties().apply {
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        load(localPropertiesFile.inputStream())
    }
}
val tmdbApiKey: String = localProperties.getProperty("TMDB_API_KEY") ?: ""

android {
    namespace = "com.cinetrack.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.cinetrack.app"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "0.1.0-phase0"

        buildConfigField("String", "TMDB_API_KEY", "\"$tmdbApiKey\"")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

jacoco {
    toolVersion = "0.8.12"
}

tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("testDebugUnitTest")

    reports {
        xml.required.set(true)
        html.required.set(true)
    }

    /**
     * Kenapa perlu exclude ini semua: kode yang di-GENERATE otomatis (Hilt,
     * R.java, BuildConfig, Compose compiler internals) itu bukan kode yang
     * KAMU tulis — memasukkannya ke perhitungan coverage cuma bikin angka
     * menyesatkan (baik terlalu tinggi kalau kebetulan "tersentuh", atau
     * terlalu rendah kalau tidak — dua-duanya tidak mencerminkan kualitas
     * kode kamu yang sebenarnya).
     */
    val fileFilter = listOf(
        "**/R.class", "**/R\$*.class", "**/BuildConfig.*", "**/Manifest*.*",
        "**/*Test*.*", "android/**/*.*",
        "**/*_Hilt*.*", "**/Hilt_*.*", "**/*_Factory.*", "**/*_MembersInjector.*",
        "**/Dagger*Component*.*", "**/*Module_*Factory.*",
        "**/ComposableSingletons\$*.*", "**/*\$Companion.*"
    )

    val debugTree = fileTree("${layout.buildDirectory.get()}/tmp/kotlin-classes/debug") {
        exclude(fileFilter)
    }
    val mainSrc = "${projectDir}/src/main/java"

    sourceDirectories.setFrom(files(mainSrc))
    classDirectories.setFrom(files(debugTree))
    executionData.setFrom(fileTree(layout.buildDirectory.get()) {
        include("**/*.exec", "**/*.ec")
    })
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.material)
    implementation(libs.androidx.recyclerview)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.moshi)
    implementation(libs.okhttp.logging)
    implementation(libs.moshi.kotlin)
    implementation(libs.kotlinx.coroutines.android)

//  Modular implementasion core/
    implementation(project(":core:model"))
    implementation(project(":core:network"))
    implementation(project(":core:common"))
    implementation(project(":core:domain"))
    implementation(project(":core:data"))

//  Compose library
    implementation(platform(libs.androidx.compose.bom))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.activity:activity-compose:1.9.2")
    implementation(libs.androidx.lifecycle.runtime.compose)
    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation(libs.coil.compose)
    implementation("androidx.compose.material:material-icons-extended:1.7.3")

//  Compose Testing Library
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    debugImplementation(libs.leakcanary.android)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
}
