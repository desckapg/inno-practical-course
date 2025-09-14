plugins {
    id("java")
}

group = "by.desckapg"

repositories {
    mavenCentral()
}
val mockitoAgent = configurations.create("mockitoAgent")

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")

    testCompileOnly("org.projectlombok:lombok:1.18.38")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.38")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testImplementation("org.assertj:assertj-core:4.0.0-M1")

    testImplementation(libs.mockito)
    mockitoAgent(libs.mockito) {
        isTransitive = false
    }
}

tasks.test {
    useJUnitPlatform()
    jvmArgs.add("-javaagent:${mockitoAgent.asPath}")
}