package utils;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.*;
import pages.LoginPage;

public class LoginHelper {

    public static void loginAsSuperAdmin(WebDriver driver) {

        LoginPage login = new LoginPage(driver);
        login.login("NoCounterSmaket", "NoCounterSmaket@123");

        // wait for dashboard
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.urlContains("/admin"));
    }
}

