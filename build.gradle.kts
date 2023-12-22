@file:Suppress("LocalVariableName")

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val targetSdkVersion  by extra("34")
    val compileSdkVersion  by extra("34")
    val buildToolsVersion  by extra("34.0.0")

    val kotlinVersion by extra("1.9.20")
    val navVersion by extra("2.7.6")
    val hiltVersion by extra("2.48.1")
    val lifecycleVersion by extra("2.6.2")
    val roomVersion by extra("2.6.1")
    val coroutinesVersion by extra("1.7.3")

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:8.1.4")

        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("com.google.dagger:hilt-android-gradle-plugin:$hiltVersion")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")
    }
}

tasks.register("clean", Delete::class) {
    delete(layout.buildDirectory)
}

subprojects {
    val afterEvaluateClosure: (Project) -> Unit = { project ->
        if (project.hasProperty("android")) {
            extensions.getByType<com.android.build.gradle.BaseExtension>().apply {
                if (project == project(":app")) {
                    val compileSdkVersion : String by rootProject.extra
                    compileSdkVersion(Integer.parseInt(compileSdkVersion))

                    val buildToolsVersion : String by rootProject.extra
                    buildToolsVersion(buildToolsVersion)
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_11
                    targetCompatibility = JavaVersion.VERSION_11
                }

                tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
                    kotlinOptions {
                        jvmTarget = "11"
                    }
                }
            }
        }
    }
    afterEvaluate(afterEvaluateClosure)
}

