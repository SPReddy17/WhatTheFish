apply{
    from("$rootDir/library-build.gradle")
}
dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.fishDataSource))
    "implementation"(project(Modules.fishDomain))

    "implementation"(Kotlinx.coroutinesCore) // need for flows
}