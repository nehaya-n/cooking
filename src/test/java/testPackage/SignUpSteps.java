package testPackage;

import cook.entities.UserRegistrationService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;

import java.util.Map;


public class SignUpSteps {

    String result;
    @Given("the system has the following registered users:")
    public void theSystemHasTheFollowingRegisteredUsers(DataTable dataTable) {
        // هنا تقوم بتحويل بيانات DataTable إلى قائمة من المستخدمين المسجلين
        UserRegistrationService signup = new UserRegistrationService();
        
        // تحويل DataTable إلى خريطة
        for (Map<String, String> row : dataTable.asMaps(String.class, String.class)) {
            String username = row.get("username");
            String email = row.get("email");
            String role = row.get("role");
            String password = row.get("password");
            String confirmPassword = row.get("confirmPassword");

            // إضافة المستخدمين إلى النظام
            signup.registerUser(username, email, role, password, confirmPassword);
        }
    }

    @When("I try to sign up with username {string}, email {string}, role {string}, password {string}, confirm password {string}")
    public void signupAttempt(String username, String email, String role, String password, String confirmPassword) {
    	UserRegistrationService signup = new UserRegistrationService();
        result = signup.registerUser(username, email, role, password, confirmPassword);
    }

    @Then("I should see {string}")
    public void iShouldSee(String expected) {
        assertEquals(expected, result);
    }

	
}


