plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "edu.cs4730.writecontact"
    compileSdk = 37

    defaultConfig {
        applicationId = "edu.cs4730.writecontact"
        minSdk = 32
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.txt")
        }
    }

    buildFeatures {
        viewBinding = true
    }

}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.material)
    implementation(libs.activity)
}
