plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("org.jetbrains.kotlin.kapt")
    id( "dagger.hilt.android.plugin")
}

android {
    namespace = "com.softwarengineering.bloodconnect"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.softwarengineering.bloodconnect"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "MAPS_API_KEY", "\"AIzaSyBexpRItDwKW4GKAZJdl37djUZnQM4fOOs\"")

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures{
        viewBinding = true
        buildConfig = true
        mlModelBinding = true
    }
}
val nav_version = "2.8.0"
apply(plugin = "androidx.navigation.safeargs.kotlin")

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.activity:activity:1.10.1")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
    implementation("org.tensorflow:tensorflow-lite-support:0.1.0")
    implementation("org.tensorflow:tensorflow-lite-metadata:0.1.0")
    implementation("com.google.firebase:firebase-firestore-ktx:25.1.2")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("androidx.recyclerview:recyclerview:1.3.2")


    // **Google Maps SDK (Harita Gösterimi)**
    implementation("com.google.android.gms:play-services-maps:18.2.0")

    // **Google Places API (Kan bağış noktalarını bulma)**
    implementation("com.google.android.libraries.places:places:4.1.0")

    // **Google Location API (Kullanıcının konumunu alma)**
    implementation("com.google.android.gms:play-services-location:21.0.1")

    implementation("com.google.maps:google-maps-services:0.17.0") {
        exclude(group = "org.apache.httpcomponents", module = "httpclient") // Compatibility Fix
    }

    // **Google Geocoding API (Adres-Konum Dönüştürme)**
    implementation("com.google.maps.android:android-maps-utils:3.4.0")

    // **Firebase Cloud Messaging (FCM) - Acil Kan Bağışı Bildirimleri İçin**
    //implementation("com.google.firebase:firebase-messaging-ktx:24.1.0")
    //implementation("com.google.firebase:firebase-analytics-ktx:21.4.0")

    // **Networking - Retrofit (API Çağrıları İçin)**
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // **Dependency Injection (Daha Modüler Kod için)**
    implementation("io.insert-koin:koin-android:3.5.3")

    // **Lifecycle ViewModel & LiveData (MVVM Yapısı İçin)**
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")

    implementation ("com.android.volley:volley:1.2.1")

// **Kotlin Coroutines for async API calls**
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    implementation("androidx.fragment:fragment-ktx:1.6.1") // Required for Fragment Support
    implementation("androidx.navigation:navigation-fragment:$nav_version")
    implementation("androidx.navigation:navigation-ui:$nav_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.5.1")
    implementation("androidx.activity:activity-ktx:1.6.1")
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")


    implementation ("com.google.firebase:firebase-auth:21.1.0")


        implementation ("androidx.compose.ui:ui-text-google-fonts:1.7.8")

}

