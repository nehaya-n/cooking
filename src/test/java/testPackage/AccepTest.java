package testPackage;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources",   // مكان ملفات .feature
    glue = "testPackage",              // مكان step definitions
    plugin = { "pretty", "html:target/cucumber-report.html" },
    monochrome = true,
    snippets = SnippetType.CAMELCASE
)
public class AccepTest {
}
