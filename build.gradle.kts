plugins {
    java

    // Apply the application plugin to add support for building an application
    application
}

version = "1.0-SNAPSHOT"

application {
    // Define the main class for the application
    mainClassName = "com.vogella.java.library.okhttp.TestMain"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.squareup.okhttp3:okhttp:3.12.1")
    implementation("org.json:json:20180813")
    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

