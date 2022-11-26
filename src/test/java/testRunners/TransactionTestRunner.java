package testRunners;

import api.Transaction;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.Assert;
import org.testng.annotations.Test;
import setup.Setup;
import utils.Utils;

import java.io.IOException;

public class TransactionTestRunner extends Setup {
    Transaction transaction;
    Utils utils;

    @Test(priority = 1, description = "Deposit to Agent from SYSTEM")
    public void depositToAgent() throws IOException, ConfigurationException, InterruptedException {
        transaction = new Transaction();
        int amount = 100;
        String agentPhoneNumber = prop.getProperty("Agent_Phone");
        String message = transaction.depositMoney("SYSTEM", agentPhoneNumber, amount, "AGENT");
        Thread.sleep(3000);
        Assert.assertEquals(message, "Deposit successful");
    }

    @Test(priority = 2, description = "Deposit to Customer from Agent")
    public void depositToCustomer() throws IOException, ConfigurationException, InterruptedException {
        transaction = new Transaction();
        int amount = 30;
        String agentPhoneNumber = prop.getProperty("Agent_Phone");
        String customerPhoneNumber = prop.getProperty("Customer_Phone");
        int agentBalance = Integer.parseInt(prop.getProperty("AGENT_BALANCE"));
        String message = transaction.depositMoney(agentPhoneNumber, customerPhoneNumber, amount, "CUSTOMER");
        Assert.assertEquals(message, "Deposit successful");
        Utils.setVariables("AGENT_BALANCE", String.valueOf(agentBalance - amount));
        Thread.sleep(3000);
    }

    @Test(priority = 3, description = "Check agents current balance")
    public void checkAgentBalance() throws IOException, ConfigurationException, InterruptedException {
        Thread.sleep(3000);
        transaction = new Transaction();
        String userPhone = prop.getProperty("Agent_Phone");
        int agentCurrentBalance = Integer.parseInt(prop.getProperty("AGENT_BALANCE"));
        System.out.println(agentCurrentBalance);
        String message = transaction.checkBalance(userPhone);
        Assert.assertEquals(agentCurrentBalance, transaction.getCurrentBalance());
    }

    @Test(priority = 4, description = "Check customers current balance")
    public void checkUserBalance() throws IOException, ConfigurationException, InterruptedException {
        Thread.sleep(3000);
        transaction = new Transaction();
        String userPhone = prop.getProperty("Customer_Phone");
        int customerCurrentBalance = Integer.parseInt(prop.getProperty("CUSTOMER_BALANCE"));
        String message = transaction.checkBalance(userPhone);
        Assert.assertEquals(customerCurrentBalance, transaction.getCurrentBalance());
    }

    @Test(priority = 5, description = "Customer cashOut money from Agent")
    public void cashOutToAgent() throws IOException, ConfigurationException, InterruptedException {
        Thread.sleep(3000);
        transaction = new Transaction();
        int amount = 10;
        String agentPhoneNumber = prop.getProperty("Agent_Phone");
        String customerPhoneNumber = prop.getProperty("Customer_Phone");
        int customerBalance = Integer.parseInt(prop.getProperty("CUSTOMER_BALANCE"));
        String message = transaction.cashOut(customerPhoneNumber, agentPhoneNumber, amount);
        Assert.assertEquals(message, "Withdraw successful");
        Utils.setVariables("CUSTOMER_BALANCE", String.valueOf(customerBalance - (amount + transaction.getCashOutFee())));
    }

    @Test(priority = 6, description = "Customer check balance again after cashOut")
    public void checkAgainUserBalance() throws IOException, ConfigurationException, InterruptedException {
        Thread.sleep(3000);
        transaction = new Transaction();
        String userPhone = prop.getProperty("Customer_Phone");
        int customerCurrentBalance = Integer.parseInt(prop.getProperty("CUSTOMER_BALANCE"));
        String message = transaction.checkBalance(userPhone);
        Assert.assertEquals(customerCurrentBalance, transaction.getCurrentBalance());
    }

}
