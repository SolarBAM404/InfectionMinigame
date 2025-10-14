import org.gradle.kotlin.dsl.implementation

val GITHUB_TOKEN: kotlin.String = System.getenv("GITHUB_TOKEN") ?: "TOKEN"
val GITHUB_USER: kotlin.String = System.getenv("GITHUB_USERNAME") ?: "USERNAME"

plugins {
    kotlin("jvm") version "2.2.0-RC"
    id("com.gradleup.shadow") version "8.3.0"
    id("xyz.jpenilla.run-paper") version "2.3.1"
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.17"
}

group = "me.solar"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }
    maven("https://oss.sonatype.org/content/groups/public/") {
        name = "sonatype"
    }
    maven {
        url = uri("https://maven.pkg.github.com/SolarBAM404/ApolloLibrary")
        credentials {
            username = GITHUB_USER
            password = GITHUB_TOKEN
        }
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.9-R0.1-SNAPSHOT")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("me.solarbam:ApolloLibrary:0.3.3")
    compileOnly("io.github.toxicity188:BetterModel:1.6.1")
    paperweight.paperDevBundle("1.21.9-R0.1-SNAPSHOT")

    // Lombok
    implementation("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")
}

tasks {
    runServer {
        minecraftVersion("1.21.9")
    }
}

val targetJavaVersion = 21
kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}
