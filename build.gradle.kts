plugins {
	kotlin("jvm") version "1.9.23"
	kotlin("plugin.spring") version "1.9.23"
	id("org.springframework.boot") version "3.3.5"
	id("io.spring.dependency-management") version "1.1.6"
	id("org.openapi.generator") version "7.8.0"
}

group = "be.fgov.onemrva"
version = "0.0.1-SNAPSHOT"
description = "Kotlin sandbox project"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

springBoot {
	mainClass.set("be.fgov.onemrva.kotlin_sandbox.KotlinSandboxApplicationKt")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

openApiGenerate {
	generatorName.set("kotlin")
	inputSpec.set(layout.projectDirectory.file("src/main/resources/static/openapi.yaml").asFile.path)
	outputDir.set(layout.buildDirectory.dir("generated/openapi/models").get().asFile.path)
	modelPackage.set("be.fgov.onemrva.kotlin_sandbox.generated.model")
	apiPackage.set("be.fgov.onemrva.kotlin_sandbox.generated.api")
	invokerPackage.set("be.fgov.onemrva.kotlin_sandbox.generated.invoker")
	generateApis.set(false)
	generateSupportingFiles.set(false)
	configOptions.set(
		mapOf(
			"dateLibrary" to "java8",
		),
	)
}

tasks.register("generateOpenApiClient", org.openapitools.generator.gradle.plugin.tasks.GenerateTask::class) {
	generatorName.set("kotlin")
	inputSpec.set(layout.projectDirectory.file("src/main/resources/static/openapi.yaml").asFile.path)
	outputDir.set(layout.buildDirectory.dir("generated/openapi/client").get().asFile.path)
	modelPackage.set("be.fgov.onemrva.kotlin_sandbox.generated.client.model")
	apiPackage.set("be.fgov.onemrva.kotlin_sandbox.generated.client.api")
	invokerPackage.set("be.fgov.onemrva.kotlin_sandbox.generated.client.invoker")
	configOptions.set(
		mapOf(
			"dateLibrary" to "java8",
		),
	)
}

tasks.named("compileKotlin") {
	dependsOn(tasks.named("openApiGenerate"))
}
