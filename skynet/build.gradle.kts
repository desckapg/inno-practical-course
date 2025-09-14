plugins {
    id("java")
}

group = "by.desckapg"

repositories {
    mavenCentral()
}

dependencies {
    implementation("ch.qos.logback:logback-classic:1.5.18")

    compileOnly("org.projectlombok:lombok:1.18.40")
    annotationProcessor("org.projectlombok:lombok:1.18.40")

    testCompileOnly("org.projectlombok:lombok:1.18.40")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.40")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.assertj:assertj-core:4.0.0-M1")
    testImplementation("org.mockito:mockito-core:5.+")
}

tasks.test {
    useJUnitPlatform()
}