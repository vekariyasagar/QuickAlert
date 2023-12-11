plugins {
    id("com.android.application")
}

android {
    namespace = "com.msd.project_group_7"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.msd.project_group_7"
        minSdk = 30
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
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
    buildFeatures{
        viewBinding=true
    }
}

dependencies {

    implementation("com.google.android.gms:play-services-wearable:18.1.0")
    implementation("androidx.percentlayout:percentlayout:1.0.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("androidx.wear:wear:1.3.0")
    implementation("com.google.android.gms:play-services-location:17.0.0")
    implementation("com.google.android.libraries.places:places:2.0.0")
    implementation("com.google.android.gms:play-services-places:17.0.0")
    implementation("androidx.multidex:multidex:2.0.0")
    implementation("com.android.volley:volley:1.2.1")
}