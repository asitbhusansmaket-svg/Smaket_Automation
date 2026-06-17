package tests;

import org.testng.annotations.Test;
import base.BaseTest;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test
    public void validLogin() {
        LoginPage login = new LoginPage(driver);
        login.login("Pradosh123", "Pradosh@123");
    }
}
