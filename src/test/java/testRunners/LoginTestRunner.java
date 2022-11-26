package testRunners;

import api.Login;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTestRunner {
    @Test(priority = 1, description = "Login as an admin")
    public void doLogin() throws ConfigurationException, IOException {
        Login login = new Login();
        String message = login.loginApi("salman@grr.la", "1234");
        Assert.assertEquals(message, "Login successfully");
    }
}
