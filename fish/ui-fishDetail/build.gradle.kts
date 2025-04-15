apply{
    from("$rootDir/android-library-build.gradle")
}
dependencies{

    "implementation"(project(Modules.core))
    "implementation"(project(Modules.components))
    "implementation"(project(Modules.fishDomain))
    "implementation"(project(Modules.fishInteractors))
    "implementation"(Coil.coil)

    // to convert html to string
    "implementation" ("org.jsoup:jsoup:1.11.1")

    "androidTestImplementation"(project(Modules.fishDataSourceTest))
    "androidTestImplementation"(ComposeTest.uiTestJunit4)
    "debugImplementation"(ComposeTest.uiTestManifest)
    "androidTestImplementation"(Junit.junit4)
}