import io.github.andrefigas.rustjni.reflection.Visibility

plugins {
    alias(libs.plugins.android.application)
    id("io.github.andrefigas.rustjni") version "0.1.0"
}

// Replaces externalNativeBuild { cmake { ... } } from the default Native C++ template.
// Rust sources live in src/main/rust, where C++ sources would live in src/main/cpp.
rustJni {
    rustPath = "./app/src/main/rust"
    libName = "{{lib_name}}"
    jniHost = "{{package_name}}.MainActivity"
    jniMethodsVisibility = Visibility.PUBLIC
    ndkVersion = "27.1.12297006"
    architectures {
        {%- if use_armv7 %}
        armv7_linux_androideabi("armv7a-linux-androideabi24-clang")
        {%- endif %}
        {%- if use_arm64 %}
        aarch64_linux_android("aarch64-linux-android24-clang")
        {%- endif %}
        {%- if use_x86 %}
        i686_linux_android("i686-linux-android24-clang")
        {%- endif %}
        {%- if use_x86_64 %}
        x86_64_linux_android("x86_64-linux-android24-clang")
        {%- endif %}
    }
    rustVersion = ">=1.79.0"
}

android {
    namespace = "{{package_name}}"
    compileSdk = 37

    defaultConfig {
        applicationId = "{{package_name}}"
        minSdk = {{min_sdk}}
        targetSdk = 37
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
