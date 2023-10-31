//package CucumberOptions;
//import io.cucumber.testng.AbstractTestNGCucumberTests;
//import io.cucumber.testng.CucumberOptions;
//
//@CucumberOptions(features="src\\test\\resources\\Features", glue="StepDefination",  
//monochrome = true )
//public class TestNGRunner extends AbstractTestNGCucumberTests{
//
//}
package CucumberOptions;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src\\test\\resources\\Features", glue = "StepDefination", monochrome = true, tags = "@MangeProfile or @Login", plugin = {
		"html:target/cucumber.html", "json:target/cucumber.json",
		"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
		"rerun:target/failed_scenarios.txt"})
public class TestNGRunner extends AbstractTestNGCucumberTests {
	
	

	}


