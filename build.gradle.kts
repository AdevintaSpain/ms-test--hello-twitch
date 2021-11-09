import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("idea")
	id("org.springframework.boot") version "2.5.6"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("org.unbroken-dome.test-sets") version "4.0.0"
	kotlin("jvm") version "1.5.31"
	kotlin("plugin.spring") version "1.5.31"
}

group = "com.adevinta"
version = "1.0.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

extra["springCloudVersion"] = "2020.0.4"

testSets {
  create("integrationTest") {}
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-actuator")
  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

  // Database
  implementation("org.springframework.boot:spring-boot-starter-jdbc")
  implementation("org.postgresql:postgresql:42.3.1")
  implementation("org.flywaydb:flyway-core:8.0.2")

  // AWS Parameter Store
  implementation("org.springframework.cloud:spring-cloud-starter-aws-parameter-store-config:2.2.6.RELEASE") {
    exclude("com.amazonaws", "aws-java-sdk-cloudformation")
    exclude("com.amazonaws", "aws-java-sdk-s3")
    exclude("com.amazonaws", "aws-java-sdk-ec2")
  }

  // Bootstrap (to be deprecated)
  implementation("org.springframework.cloud:spring-cloud-starter-bootstrap")

  // Kotlin
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

  // Api Docs
  implementation("io.springfox:springfox-boot-starter:3.0.0")

  // Access Log
  implementation("net.rakugakibox.spring.boot:logback-access-spring-boot-starter:2.7.1")
  implementation("net.logstash.logback:logstash-logback-encoder:5.1")

  // Test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("com.nhaarman:mockito-kotlin:1.6.0")
  testImplementation("io.rest-assured:spring-mock-mvc:4.4.0")
  testImplementation("com.jayway.jsonpath:json-path-assert:2.6.0")

  // Integration Test
  "integrationTestImplementation"("org.awaitility:awaitility:4.1.0")
  "integrationTestImplementation"("org.testcontainers:testcontainers:1.16.0")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks["check"].dependsOn("integrationTest")
tasks["integrationTest"].shouldRunAfter("test")
