plugins {
    `java-library`
}

dependencies {
    compileOnly("org.jetbrains:annotations:26.0.2-1")
    compileOnly("io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
    
    implementation("com.github.OPmasterLEO:MasterCombatPro-API:1.1.0")
}

java {
    withSourcesJar()
}