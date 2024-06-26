plugins {
    id("java")
}

group = "${parent!!.group}.core"
version = parent!!.version

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":inflib-lib"))
    implementation(project(":inflib-core"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}