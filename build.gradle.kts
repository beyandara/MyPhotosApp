// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    id("com.android.library") version "8.1.1" apply false
}

buildscript {
    extra.apply {
        set("lifecycle_version", "2.6.2")
        set("compose_compiler_version", "1.5.2")
        set("retrofit2_version", "2.9.0")
    }
}
