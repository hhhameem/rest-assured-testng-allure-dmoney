package testRunners;

import api.UpdateUser;
import com.github.javafaker.Faker;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class UpdateUserTestRunner {
    @Test(priority = 1, description = "Update user phone number")
    public void updateUserPhoneNumber() throws IOException, ConfigurationException {
        UpdateUser updateUser = new UpdateUser();
        Faker faker = new Faker();
        String phone_number = "017"+faker.number().digits(8);
        String message = updateUser.updateUser(phone_number);
        Assert.assertEquals(message, "User updated successfully");
    }
}
