plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.serialization")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

val composeUICompilerVersion = "1.3.2"

val nameSpace = "com.agoines.goods"
android {
    namespace = nameSpace
    compileSdk = 33

    buildToolsVersion = "33.0.0"

    defaultConfig {
        applicationId = nameSpace
        minSdk = 28
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            setProguardFiles(listOf("proguard-rules.pro"))
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = composeUICompilerVersion
    }

    packagingOptions {
        resources.excludes.addAll(listOf("META-INF/**", "kotlin/**", "google/**", "**.bin"))
    }

}

tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class).all {
    kotlinOptions.freeCompilerArgs = listOf("-Xcontext-receivers")
}

dependencies {
    val composeUIVersion = "1.4.0-alpha02"
    val navigationVersion = "2.5.3"
    val lifecycleVersion = "2.6.0-alpha03"
    val cameraxVersion = "1.3.0-alpha01"
    // 浏览器使用
    implementation("androidx.browser:browser:1.4.0")
    // 二维码识别
    implementation("com.journeyapps:zxing-android-embedded:4.3.0")
    implementation("com.google.zxing:core:3.5.1")
//    implementation("androidx.camera:camera-core:${cameraxVersion}")
//    implementation("androidx.camera:camera-camera2:${cameraxVersion}")
//    implementation("androidx.camera:camera-lifecycle:${cameraxVersion}")
//    implementation("androidx.camera:camera-view:${cameraxVersion}")

    // 网络组件库
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.github.liangjingkanji:Net:3.5.1")

    // hilt 注入
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-compiler:2.44")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")

    // Datastore
    implementation("androidx.datastore:datastore-preferences:1.1.0-alpha01")

    // moshi 解析库
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")

    // compose 所需
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.0-alpha03")
    implementation("androidx.activity:activity-compose:1.6.1")

    // compose
    implementation("androidx.compose.ui:ui:$composeUIVersion")
    implementation("androidx.compose.foundation:foundation-layout:$composeUIVersion")
    implementation("androidx.compose.material:material:$composeUIVersion")
    implementation("androidx.navigation:navigation-compose:$navigationVersion")

    // compose icon
    implementation("androidx.compose.material:material-icons-core:$composeUIVersion")
    implementation("androidx.compose.material:material-icons-extended:$composeUIVersion")

    // compose 组件
    implementation("me.saket.swipe:swipe:1.0.0")
    implementation("me.saket.extendedspans:extendedspans:1.3.0")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.27.0")
    implementation("com.github.develNerd:JC-CustomSwitch:1.0.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeUIVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeUIVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeUIVersion")
}

kapt {
    correctErrorTypes = true
}