package testRunners;

import api.CreateUser;
import com.github.javafaker.Faker;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class CreateUserTestRunner {
    @Test(priority = 1, description = "Create a customer")
    public void createUser() throws IOException, ConfigurationException {
        CreateUser createUser = new CreateUser();
        Faker faker = new Faker();
        String name = faker.name().firstName();
        String email = name+"@email.com";
        String phone_number = "019"+ faker.number().digits(8);
        String message = createUser.CreateUser(name, email, "Str0ngP@ssw0rd", phone_number, "7804426791", "Customer");
        Assert.assertEquals(message, "User created successfully");
    }
    @Test(priority = 2, description = "Create an agent")
    public void createAgent() throws IOException, ConfigurationException {
        CreateUser createUser = new CreateUser();
        Faker faker = new Faker();
        String name = faker.name().firstName();
        String email = name+"@email.com";
        String phone_number = "019"+ faker.number().digits(8);
        String message = createUser.CreateUser(name, email, "Str0ngP@ssw0rd", phone_number, "7804426791", "Agent");
        Assert.assertEquals(message, "User created successfully");
    }
}
