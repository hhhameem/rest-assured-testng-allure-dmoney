package api;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.UpdateUserModel;
import org.apache.commons.configuration.ConfigurationException;
import setup.Setup;
import utils.Utils;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class UpdateUser extends Setup {
    public UpdateUser() throws IOException {
        initProperties();
    }

    public String updateUser(String phone_number) throws ConfigurationException {
        RestAssured.baseURI = prop.getProperty("BASE_URI");
        Response res = given()
                .contentType("application/json")
                .header("Authorization", prop.getProperty("TOKEN"))
                .header("X-AUTH-SECRET-KEY", prop.getProperty("X_AUTH_SECRET_KEY"))
                .body(new UpdateUserModel(phone_number))
                .when()
                .patch("/user/update/" + prop.getProperty("Customer_Id"))
                .then()
                .assertThat().statusCode(200).extract().response();
        JsonPath response = res.jsonPath();
        String message = response.get("message");
        Utils.setVariables("Customer_Phone", response.get("user.phone_number"));
        return message;
    }
}
