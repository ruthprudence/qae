package com.rankinggame.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features", // Path to your feature files
    glue = "com.rankinggame.stepdefinitions", // Package where your step definitions are located
    plugin = {"pretty", "html:target/cucumber-reports"}, // Reporting plugins
    tags = "@all" // Tags to run; use "@all" to run all scenarios
)
public class TestRunner extends AbstractTestNGCucumberTests {
}