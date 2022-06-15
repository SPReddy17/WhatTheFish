apply{
    from("$rootDir/library-build.gradle")
}
plugins{
    kotlin(KotlinPlugins.serialization) version Kotlin.version
    id(SqlDelight.plugin)
}
dependencies{
    "implementation"(project(Modules.fishDomain))
    "implementation"(Ktor.core)
    "implementation"(Ktor.clientSerialization)
    "implementation"(Ktor.android)
    "implementation"(SqlDelight.runtime)

}

sqldelight{
    database("FishDatabase"){
        packageName = "com.example.fish_datasource.cache"
        sourceFolders = listOf("sqldelight")
    }
}

