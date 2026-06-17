package pages;

import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class UtilitiesPage {

    WebDriver driver;
    WebDriverWait wait;

    // ✅ REQUIRED CONSTRUCTOR
    public UtilitiesPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    By utilitiesMenu =
        By.xpath("//a[@title='Utilities' or .//p[text()='Utilities']]");

    By cartManagement =
        By.xpath("//a[@title='Cart Management' or .//p[text()='Cart Management']]");

    By registerCart =
        By.xpath("//a[contains(@href,'CartRegister')]");

    // ALREADY WORKING METHOD
    public void goToRegisterCart() {
        wait.until(ExpectedConditions.elementToBeClickable(utilitiesMenu)).click();
        wait.until(ExpectedConditions.elementToBeClickable(cartManagement)).click();
        wait.until(ExpectedConditions.elementToBeClickable(registerCart)).click();
    }

    // ALREADY WORKING METHOD
    public void goToCartManagement() {
        wait.until(ExpectedConditions.elementToBeClickable(utilitiesMenu)).click();
        wait.until(ExpectedConditions.elementToBeClickable(cartManagement)).click();
    }
}
