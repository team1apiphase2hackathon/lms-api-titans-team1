package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepDefinitions", "hooks"},
<<<<<<< HEAD
        tags = "(@LoginModule and @e2e) or (@programmodule and @e2e) or (@batch and @e2e) or (@userController and @e2e) or (@skill and @e2e)",
=======
       // tags = "(@LoginModule and @e2e) or (@userController and @wip)",
>>>>>>> a20fe16913c2202aa532b1861f1fbd9217a81316
        plugin = {
                "pretty",
                "html:target/cucumber-report.html",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        }
)
public class TestRunner extends AbstractTestNGCucumberTests{


	
}
