package testRunner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions
        (
//                features = ".//Features/Customers.feature", //Run a specific feature
//                features = {".//Features/Login.feature",".//Features/Customers.feature"}, //Run multiple features
                features = ".//Features/", //Run all features
                glue = "stepDefinitions",
                dryRun = false,
                monochrome = true,
                plugin = {"pretty", "html:test-output"},
//                tags = {"@Sanity"} //Run specific scenarios
                tags = {"@Sanity or @Regression"} //Run several scenarios
        )
public class TestRun {
}
