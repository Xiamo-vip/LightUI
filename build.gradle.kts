plugins {
    id("java")
    kotlin("jvm")
}

group = "top.xiamoi"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://packages.jetbrains.team/maven/p/skija/maven")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("org.jetbrains.skija:skija-windows:0.93.6")
    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    useJUnitPlatform()
}


repositories {
    mavenCentral()
    maven("https://redirector.kotlinlang.org/maven/compose-dev")
}

val osName = System.getProperty("os.name")
val targetOs = when {
    osName == "Mac OS X" -> "macos"
    osName.startsWith("Win") -> "windows"
    osName.startsWith("Linux") -> "linux"
    else -> error("Unsupported OS: $osName")
}

val osArch = System.getProperty("os.arch")
val targetArch = when (osArch) {
    "x86_64", "amd64" -> "x64"
    "aarch64" -> "arm64"
    else -> error("Unsupported arch: $osArch")
}

val skkioVersion = "0.8.9" // or any more recent version
val target = "${targetOs}-${targetArch}"
dependencies {
    implementation("org.jetbrains.skiko:skiko-awt-runtime-$target:$skkioVersion")
}
kotlin {
    jvmToolchain(21)
}