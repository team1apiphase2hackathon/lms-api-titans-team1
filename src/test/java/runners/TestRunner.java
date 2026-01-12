package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepDefinitions", "hooks"},
        tags = "@skill and (@post or @get or @getAll or @delete)",
        plugin = {
                "pretty",
                "html:target/cucumber-report.html"
        }
)
public class TestRunner extends AbstractTestNGCucumberTests{


	
}
