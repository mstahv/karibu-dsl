dependencies {
    api(project(":karibu-dsl"))

    api("com.github.mvysny.dynatest:dynatest:${properties["dynatest_version"]}")
    api("com.github.mvysny.kaributesting:karibu-testing-v10:${properties["kaributesting_version"]}")
    api("org.slf4j:slf4j-simple:${properties["slf4j_version"]}")

    // Vaadin
    // don't compile-depend on vaadin-core anymore: the app itself should manage Vaadin dependencies, for example
    // using the gradle-flow-plugin or direct dependency on vaadin-core. The reason is that the app may wish to use the
    // npm mode and exclude all webjars.
    compileOnly("com.vaadin:vaadin-core:${properties["vaadin_version"]}")
    testImplementation("com.vaadin:vaadin-core:${properties["vaadin_version"]}")
}
