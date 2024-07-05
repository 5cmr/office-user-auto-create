plugins {
    kotlin("jvm")
}

version = "unspecified"

dependencies {
    //implementation(kotlin("stdlib"))
    testImplementation("junit:junit:4.13.2")

    api("org.jetbrains.exposed:exposed-core:0.37.3")

    implementation("commons-beanutils:commons-beanutils:1.9.4")
    api("org.apache.commons:commons-configuration2:2.9.0")
    api("org.apache.commons:commons-lang3:3.12.0")  // apache工具包
    api("com.github.binarywang:weixin-java-mp:4.4.9.B")
    implementation("org.jsoup:jsoup:1.15.4")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.14.2")

    // 公共库
    api("cn.hutool:hutool-all:5.8.16")
}
