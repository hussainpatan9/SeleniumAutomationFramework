package CucumberOptions;

import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "@target/failed_scenarios.txt", glue = "StepDefination", monochrome = true, plugin = {
		"html:target/cucumber.html", "json:target/cucumber.json",
		"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" })
public class FailedTestRunner {

}
