import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.ofSourceSet
import com.google.protobuf.gradle.plugins
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.util.prefixIfNot

val developmentOnly: Configuration by configurations.creating
val kotlinVersion: String by project
val micronautVersion: String by project
val reactorVersion: String by project
val kMongoVersion: String by project
val grpcPgvVersion: String by project
val grpcServicesVersion: String by project
val grpcReactorVersion: String by project
val junitVersion: String by project
val mockkVersion: String by project
val assertJVersion: String by project
val testContainersVersion: String by project
val basePackage = "com.github.jntakpe.holidays"

plugins {
    val kotlinVersion = "1.3.72"
    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    kotlin("plugin.allopen") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    id("com.google.protobuf") version "0.8.12"
    id("com.github.johnrengelman.shadow") version "6.0.0"
    application
}

version = "0.1"
group = "com.github.jntakpe"

repositories {
    mavenCentral()
    jcenter()
}

configurations {
    developmentOnly
}

dependencies {
    kapt(platform("io.micronaut:micronaut-bom:$micronautVersion"))
    kapt("io.micronaut:micronaut-inject-java")
    kapt("org.litote.kmongo:kmongo-annotation-processor:$kMongoVersion")
    implementation(platform("io.micronaut:micronaut-bom:$micronautVersion"))
    implementation(platform("io.projectreactor:reactor-bom:$reactorVersion"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    implementation("javax.annotation:javax.annotation-api")
    implementation("io.micronaut:micronaut-inject")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut.grpc:micronaut-grpc-runtime")
    implementation("io.micronaut.mongodb:micronaut-mongo-reactive")
    implementation("io.micronaut.reactor:micronaut-reactor")
    implementation("io.envoyproxy.protoc-gen-validate:pgv-java-grpc:$grpcPgvVersion")
    implementation("io.grpc:grpc-services:$grpcServicesVersion")
    implementation("com.salesforce.servicelibs:reactor-grpc-stub:$grpcReactorVersion")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.litote.kmongo:kmongo-async-serialization:$kMongoVersion")
    runtimeOnly("ch.qos.logback:logback-classic")
    kaptTest(platform("io.micronaut:micronaut-bom:$micronautVersion"))
    kaptTest("io.micronaut:micronaut-inject-java")
    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("io.micronaut.test:micronaut-test-junit5")
    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("io.projectreactor:reactor-test:")
    testImplementation("org.assertj:assertj-core:$assertJVersion")
    testImplementation("org.testcontainers:testcontainers:$testContainersVersion")
    testImplementation("org.testcontainers:junit-jupiter:$testContainersVersion")
    testImplementation("org.testcontainers:mongodb:$testContainersVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

application {
    mainClassName = "$basePackage.ApplicationKt"
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
}

allOpen {
    annotation("io.micronaut.aop.Around")
}

kapt {
    arguments {
        arg("micronaut.processing.incremental", true)
        arg("micronaut.processing.annotations", "$basePackage.*")
        arg("micronaut.processing.group", basePackage)
        arg("micronaut.processing.module", "holidays")
    }
}

sourceSets {
    main {
        java {
            val dirs = listOf("grpc", "java", "javapgv", "reactor")
            srcDirs(dirs.map { it.prefixIfNot("build/generated/source/proto/main/") })
        }
    }
}

protobuf {
    val grpcId = "grpc"
    val javaPgvId = "javapgv"
    val reactorId = "reactor"
    protoc {
        artifact = "com.google.protobuf:protoc:3.12.2"
    }
    plugins {
        id(grpcId) {
            artifact = "io.grpc:protoc-gen-grpc-java:1.30.2"
        }
        id(javaPgvId) {
            artifact = "io.envoyproxy.protoc-gen-validate:protoc-gen-validate:0.3.0"
        }
        id(reactorId) {
            artifact = "com.salesforce.servicelibs:reactor-grpc:1.0.1"
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.generateDescriptorSet = true
            it.descriptorSetOptions.includeImports = true
            it.plugins {
                id(grpcId)
                id(javaPgvId) {
                    option("lang=java")
                }
                id(reactorId)
            }
        }
    }
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "11"
            javaParameters = true
        }
    }

    withType<Test> {
        classpath = classpath.plus(developmentOnly)
        useJUnitPlatform()
    }

    named<JavaExec>("run") {
        doFirst {
            jvmArgs = listOf("-noverify", "-XX:TieredStopAtLevel=1", "-Dcom.sun.management.jmxremote")
            classpath = classpath.plus(developmentOnly)
            if (gradle.startParameter.isContinuous) {
                systemProperties(
                    "micronaut.io.watch.restart" to "true",
                    "micronaut.io.watch.enabled" to "true",
                    "micronaut.io.watch.paths" to "src/main"
                )
            }
        }
    }

    named<ShadowJar>("shadowJar") {
        mergeServiceFiles()
    }
}
