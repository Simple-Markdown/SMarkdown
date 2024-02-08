import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    kotlin("plugin.lombok") version "1.9.0"
    id("io.freefair.lombok") version "8.1.0"
}

group = "indi.midreamsheep.app.markdown"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    maven("https://maven.aliyun.com/repository/public/")
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.windows_x64)
    implementation(compose.desktop.linux_arm64)
    implementation(compose.desktop.linux_x64)
    implementation(compose.material3)
    implementation(compose.ui)
    //导入本地jar包
    implementation(files("libs/SIOC.jar"))
    implementation(files("libs/DenpendenceInjector.jar"))
    implementation(files("libs/PackageScanner.jar"))
    //导入lombok
    implementation("org.projectlombok:lombok:1.18.20")
    //导入hutool
    implementation("cn.hutool:hutool-all:5.7.10")
    //日志工具
    implementation("org.slf4j:slf4j-api:1.7.32")
    implementation("org.slf4j:slf4j-simple:1.7.32")
    //导入log4j2
    implementation("org.apache.logging.log4j:log4j-api:2.14.1")
    implementation("org.apache.logging.log4j:log4j-core:2.14.1")
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "markdown"
            packageVersion = "1.0.0"
        }
    }
}
