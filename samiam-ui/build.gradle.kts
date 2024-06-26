plugins {
    id("java")
    id("application")

}
application { mainClass.set("edu.ucla.belief.ui.UI") }

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":inflib-lib"))
    implementation(project(":inflib-core"))
    implementation(project(":samiam-core"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}