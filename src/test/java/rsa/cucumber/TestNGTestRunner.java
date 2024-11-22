package rsa.cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

//cucumber-> TestNG, junit

@CucumberOptions(features = "src/test/java/rsa/cucumber", glue = "rsa.StepDefinitions", 
monochrome = true, tags = "@ErrorValidation", plugin = {"html:target/cucumber.html"})
public class TestNGTestRunner extends AbstractTestNGCucumberTests{

}
