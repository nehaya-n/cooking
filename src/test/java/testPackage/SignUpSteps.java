package testPackage;

import cook.entities.UserRegistrationService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.junit.Assert;

import java.util.Map;

public class SignUpSteps {

    private UserRegistrationService service = new UserRegistrationService();
    private String registrationResult;

    @Given("the system has the following registered users:")
    public void the_system_has_registered_users(DataTable table) {
        service.clearData();  // تنظيف البيانات بين السيناريوهات
        for (Map<String, String> row : table.asMaps(String.class, String.class)) {
            String username = row.get("username");
            String email = row.get("email");
            String role = row.get("role");
            String password = row.get("password");
            String confirmPassword = row.get("confirmPassword");
            service.registerUser(username, email, role, password, confirmPassword);
        }
    }

    @When("I try to sign up with username {string}, email {string}, role {string}, password {string}, confirm password {string}")
    public void i_try_to_sign_up_with(String username, String email, String role, String password, String confirmPassword) {
        registrationResult = service.registerUser(username, email, role, password, confirmPassword);
    }

    @Then("I should see {string}")
    public void i_should_see(String expectedMessage) {
        Assert.assertEquals(expectedMessage, registrationResult);
    }
}
