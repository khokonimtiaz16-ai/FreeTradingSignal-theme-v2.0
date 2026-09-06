plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    // ...
    androidResources {
        noCompress += "tflite"
    }
}
    namespace = "com.example.freetradingsignal"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.freetradingsignal"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    // TensorFlow Lite Core and Support Libraries
    implementation("org.tensorflow:tensorflow-lite:2.14.0")
    implementation("org.tensorflow:tensorflow-lite-support:0.4.4")
}
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
}
