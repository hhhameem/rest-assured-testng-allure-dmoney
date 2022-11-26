package api;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.TransactionModel;
import org.apache.commons.configuration.ConfigurationException;
import setup.Setup;
import utils.Utils;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class Transaction extends Setup {
    private int currentBalance;
    private int cashOutFee;

    public Transaction() throws IOException {
        initProperties();
    }

    public int getCashOutFee() {
        return cashOutFee;
    }

    public void setCashOutFee(int cashOutFee) {
        this.cashOutFee = cashOutFee;
    }

    public int getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(int currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String depositMoney(String from_account, String to_account, int amount, String balanceHolder) throws ConfigurationException {
        RestAssured.baseURI = prop.getProperty("BASE_URI");
        Response res = given()
                .contentType("application/json")
                .header("Authorization", prop.getProperty("TOKEN"))
                .header("X-AUTH-SECRET-KEY", prop.getProperty("X_AUTH_SECRET_KEY"))
                .body(new TransactionModel(from_account, to_account, amount))
                .when()
                .post("/transaction/deposit")
                .then()
                .assertThat().statusCode(201).extract().response();
        JsonPath response = res.jsonPath();
        String message = response.get("message");
        setCurrentBalance(response.get("currentBalance"));
        Utils.setVariables(balanceHolder + "_BALANCE", String.valueOf(amount));
        System.out.println(res.asString());
        return message;
    }

    public String cashOut(String from_account, String to_account, int amount) throws ConfigurationException {
        RestAssured.baseURI = prop.getProperty("BASE_URI");
        Response res = given()
                .contentType("application/json")
                .header("Authorization", prop.getProperty("TOKEN"))
                .header("X-AUTH-SECRET-KEY", prop.getProperty("X_AUTH_SECRET_KEY"))
                .body(new TransactionModel(from_account, to_account, amount))
                .when()
                .post("/transaction/withdraw")
                .then()
                .assertThat().statusCode(201).extract().response();
        JsonPath response = res.jsonPath();
        String message = response.get("message");
        setCurrentBalance(response.get("currentBalance"));
        setCashOutFee(response.get("fee"));
        return message;
    }

    public String checkBalance(String phoneNumber) throws ConfigurationException {
        RestAssured.baseURI = prop.getProperty("BASE_URI");
        Response res = given()
                .contentType("application/json")
                .header("Authorization", prop.getProperty("TOKEN"))
                .header("X-AUTH-SECRET-KEY", prop.getProperty("X_AUTH_SECRET_KEY"))
                .when()
                .get("/transaction/balance/" + phoneNumber)
                .then()
                .assertThat().statusCode(200).extract().response();
        JsonPath response = res.jsonPath();
        System.out.println(res.asString());
        String message = response.get("message");
        setCurrentBalance(response.get("balance"));
        return message;
    }
}
