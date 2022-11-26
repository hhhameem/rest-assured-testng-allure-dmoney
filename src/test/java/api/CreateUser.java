package api;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.CreateUserModel;
import org.apache.commons.configuration.ConfigurationException;
import setup.Setup;
import utils.Utils;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class CreateUser extends Setup {
    public CreateUser() throws IOException {
        initProperties();
    }

    public String CreateUser(String name, String email, String password, String phone_number, String nid, String role) throws ConfigurationException {
        RestAssured.baseURI = prop.getProperty("BASE_URI");
        Response res = given()
                .contentType("application/json")
                .header("Authorization", prop.getProperty("TOKEN"))
                .header("X-AUTH-SECRET-KEY", prop.getProperty("X_AUTH_SECRET_KEY"))
                .body(new CreateUserModel(name, email, password, phone_number, nid, role))
                .when()
                .post("/user/create")
                .then()
                .assertThat().statusCode(201).extract().response();

        JsonPath response = res.jsonPath();
        String message = response.get("message");
        String phoneNumber = response.get("user.phone_number");
        int id = response.get("user.id");
        Utils.setVariables(role + "_Phone", phoneNumber);
        Utils.setVariables(role + "_Id", String.valueOf(id));
        return message;
    }
}
