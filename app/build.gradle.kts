import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = Android.compileSdk
    buildToolsVersion = Android.buildTools

    defaultConfig {
        applicationId = Android.appId
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
        versionCode = Android.versionCode
        versionName = Android.versionName
        testInstrumentationRunner = "com.example.whatthefish.CustomTestRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Compose.composeVersion
    }
    packagingOptions {
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
    }
}

dependencies{
    implementation(project(Modules.core))
    implementation(project(Modules.fishDomain))
    implementation(project(Modules.fishDataSource))
    implementation(project(Modules.fishInteractors))
    implementation(project(Modules.ui_fishList))
    implementation(project(Modules.ui_fishDetail))

    implementation(Accompanist.animations)

    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)
    implementation(AndroidX.lifecycleVmKtx)

    implementation(Coil.coil)
    implementation(Compose.activity)
    implementation(Compose.ui)
    implementation(Compose.material)
    implementation(Compose.tooling)
    implementation(Compose.navigation)
    implementation(Compose.hiltNavigation)

    implementation(Google.material)
    implementation(Hilt.android)
    implementation("androidx.test:runner:1.4.0")
    kapt(Hilt.compiler)

    implementation(SqlDelight.androidDriver)

    implementation(project(Modules.fishDataSourceTest))
    androidTestImplementation(AndroidXTest.runner)
    androidTestImplementation(ComposeTest.uiTestJunit4)
    androidTestImplementation(HiltTest.hiltAndroidTesting)
    kaptAndroidTest(Hilt.compiler)
    androidTestImplementation(Junit.junit4)

}










