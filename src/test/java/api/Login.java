package api;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.LoginModel;
import org.apache.commons.configuration.ConfigurationException;
import setup.Setup;
import utils.Utils;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class Login extends Setup {
    public Login() throws IOException {
        initProperties();
    }

    public String loginApi(String email, String password) throws ConfigurationException {
        RestAssured.baseURI = prop.getProperty("BASE_URI");
        Response res = given()
                .contentType("application/json")
                .body(new LoginModel(email, password))
                .when()
                .post("/user/login")
                .then()
                .assertThat().statusCode(200).extract().response();

        JsonPath response = res.jsonPath();
        String message = response.get("message");
        Utils.setVariables("TOKEN", response.get("token"));
        return message;
    }
}
