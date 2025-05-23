apply {
    from("$rootDir/android-library-build.gradle")
}
dependencies {

    "implementation"(project(Modules.core))
    "implementation"(project(Modules.components))
    "implementation"(project(Modules.fishDomain))
    "implementation"(project(Modules.fishInteractors))

    "implementation"(SqlDelight.androidDriver)
    "implementation"(Coil.coil)
    "implementation"(Hilt.android)
    "kapt"(Hilt.compiler)

    "androidTestImplementation"(project(Modules.fishDataSourceTest))
    "androidTestImplementation"(ComposeTest.uiTestJunit4)
    "debugImplementation"(ComposeTest.uiTestManifest)
    "androidTestImplementation"(Junit.junit4)

}