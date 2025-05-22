


package testPackage;

import cook.entities.UserRegistrationService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import static org.junit.Assert.*;

import java.util.Map;

public class SignUpSteps {

    String result;
    boolean loginResult;
    String retrievedRole;
    UserRegistrationService signup = new UserRegistrationService(); // ثابت عبر السيناريوهات

    @Given("the system has the following registered users:")
    public void theSystemHasTheFollowingRegisteredUsers(DataTable dataTable) {
        for (Map<String, String> row : dataTable.asMaps(String.class, String.class)) {
            String username = row.get("username");
            String email = row.get("email");
            String role = row.get("role");
            String password = row.get("password");
            String confirmPassword = row.get("confirmPassword");

            signup.registerUser(username, email, role, password, confirmPassword);
        }
    }

    @When("I try to sign up with username {string}, email {string}, role {string}, password {string}, confirm password {string}")
    public void signupAttempt(String username, String email, String role, String password, String confirmPassword) {
        result = signup.registerUser(username, email, role, password, confirmPassword);
    }

    @Then("I should see {string}")
    public void iShouldSee(String expected) {
        assertEquals(expected, result);
    }

    // ✅ إضافات لتغطية كل الدوال:

    @Then("the username {string} should be valid")
    public void validUsernameCheck(String username) {
        assertTrue(signup.isValidUsername(username));
    }

    @Then("the username {string} should be invalid")
    public void invalidUsernameCheck(String username) {
        assertFalse(signup.isValidUsername(username));
    }

    @Then("the email {string} should be valid")
    public void validEmailCheck(String email) {
        assertTrue(signup.isValidEmail(email));
    }

    @Then("the email {string} should be invalid")
    public void invalidEmailCheck(String email) {
        assertFalse(signup.isValidEmail(email));
    }

    @Then("the password {string} should be valid")
    public void validPasswordCheck(String password) {
        assertTrue(signup.isValidPassword(password));
    }

    @Then("the password {string} should be invalid")
    public void invalidPasswordCheck(String password) {
        assertFalse(signup.isValidPassword(password));
    }

    @When("I try to login with username {string} and password {string}")
    public void loginAttempt(String username, String password) {
        loginResult = signup.isValidLogin(username, password);
    }

    @Then("the login result should be {string}")
    public void checkLoginResult(String expected) {
        boolean expectedBoolean = Boolean.parseBoolean(expected);
        assertEquals(expectedBoolean, loginResult);
    }

    @Then("the login result should be true")
    public void loginShouldSucceed() {
        assertTrue(loginResult);
    }

    @Then("the login result should be false")
    public void loginShouldFail() {
        assertFalse(loginResult);
    }

    @When("I request the role for username {string}")
    public void getRoleForUser(String username) {
        retrievedRole = signup.getRoleByUsername(username);
    }

    @Then("the system should return the role {string}")
    public void verifyReturnedRole(String expectedRole) {
        assertEquals(expectedRole, retrievedRole);
    }
}
