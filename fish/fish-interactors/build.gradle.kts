apply{
    from("$rootDir/library-build.gradle")
}
dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.fishDataSource))
    "implementation"(project(Modules.fishDomain))

    "implementation"(Kotlinx.coroutinesCore) // need for flows

    "implementation"(project(Modules.fishDataSourceTest))
    "implementation"(Junit.junit4)
    "implementation"(Ktor.ktorClientMock)
    "implementation"(Ktor.clientSerialization)

}