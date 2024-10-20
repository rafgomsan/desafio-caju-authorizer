plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.3.4"
	id("io.spring.dependency-management") version "1.1.6"
	kotlin("plugin.jpa") version "1.9.25"
	id("org.flywaydb.flyway") version "10.19.0"
}

group = "com.caju"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.flywaydb:flyway-core")
	implementation("org.flywaydb:flyway-database-postgresql")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

buildscript {
	repositories {
		maven {
			url = uri("https://plugins.gradle.org/m2/")
		}
	}
	dependencies {
		classpath("gradle.plugin.org.flywaydb:gradle-plugin-publishing:10.19.0")
		classpath("org.flywaydb:flyway-database-postgresql:10.1.0")
	}
}

// apply(plugin = "org.flywaydb.flyway")

flyway {
	url = "jdbc:postgresql://localhost:5432/postgres?loggerLevel=OFF"
	driver = "org.postgresql.Driver"
	user = "postgres"
	password = "postgres"
	baselineOnMigrate = true
	locations = arrayOf("filesystem:src/main/resources/db/migration")
	cleanDisabled = false
	// skipExecutingMigrations = true
}

