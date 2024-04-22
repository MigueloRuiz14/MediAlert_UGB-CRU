plugins {
    id("com.android.application")
    //Para Firebase
    id("com.google.gms.google-services")
}

android {
    namespace = "com.ruiz.medialertugb_cru"
    compileSdk = 34


    defaultConfig {
        applicationId = "com.ruiz.medialertugb_cru"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {
    //DEPENDENCIAS PARA USAR FIREBASE
    implementation("com.google.firebase:firebase-analytics")
    implementation(platform("com.google.firebase:firebase-bom:32.5.0"))
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-crashlytics-buildtools:2.9.9")
    //LA UBICACION
    implementation("com.google.android.gms:play-services-location:18.0.0")
    //ENVIAR CORREOS
    implementation(files("libs\\additionnal.jar"))
    implementation(files("libs\\activation.jar"))
    implementation(files("libs\\mail.jar"))
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}