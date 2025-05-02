package testPackage;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "use_cases",                
    glue = "testPackage",                      
    plugin = {"html:target/cucumber/wikipedia.html"},
    monochrome = true,
    snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class TestRunner {
}
