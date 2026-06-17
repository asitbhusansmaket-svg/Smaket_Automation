package pages;

import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class CartRegisterPage {

    WebDriver driver;
    WebDriverWait wait;

    public CartRegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    By cartPrefix = By.id("cartPrefix");
    By cartNumber = By.id("cartNumber");
    By machineNo  = By.id("machNo");
    By submitBtn  = By.xpath("//button[normalize-space()='Submit']");

    // SweetAlert popup (SUCCESS or ERROR)
    By popupContainer = By.cssSelector("div.swal2-popup");
    By popupMessage   = By.cssSelector("div.swal2-html-container");
    By okButton       = By.xpath("//button[normalize-space()='OK']");

    public void createCart(String prefix, String number, String machine) {

        // ---- Fill Cart Prefix ----
        WebElement prefixEl =
            wait.until(ExpectedConditions.elementToBeClickable(cartPrefix));
        prefixEl.click();
        prefixEl.clear();
        prefixEl.sendKeys(prefix);
        prefixEl.sendKeys(Keys.TAB);

        // ---- Fill Cart Number ----
        WebElement numberEl =
            wait.until(ExpectedConditions.elementToBeClickable(cartNumber));
        numberEl.click();
        numberEl.clear();
        numberEl.sendKeys(number);
        numberEl.sendKeys(Keys.TAB);

        // ---- Fill Machine No ----
        WebElement machineEl =
            wait.until(ExpectedConditions.elementToBeClickable(machineNo));
        machineEl.click();
        machineEl.clear();
        machineEl.sendKeys(machine);
        machineEl.sendKeys(Keys.TAB);

        // ---- Wait until submit enabled ----
        wait.until(driver -> driver.findElement(submitBtn).isEnabled());

        // ---- Submit ----
        driver.findElement(submitBtn).click();

        // ---- HANDLE SUCCESS OR ERROR POPUP ----
        handlePopupAndReset();
    }

    // COMMON HANDLER (SUCCESS / ERROR / ALREADY EXISTS)
    private void handlePopupAndReset() {

        try {
            // Wait for popup
            wait.until(ExpectedConditions.visibilityOfElementLocated(popupContainer));

            String message = "";
            try {
                message = driver.findElement(popupMessage).getText();
            } catch (Exception ignored) {}

            System.out.println("ℹ Popup message: " + message);

            // Click OK
            wait.until(ExpectedConditions.elementToBeClickable(okButton)).click();

            // Wait until popup disappears
            wait.until(ExpectedConditions.invisibilityOfElementLocated(popupContainer));

        } catch (TimeoutException e) {
            System.out.println("⚠ No popup appeared (unexpected case)");
        }

        // ---- Clear fields for next entry ----
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartPrefix));
        driver.findElement(cartPrefix).clear();
        driver.findElement(cartNumber).clear();
        driver.findElement(machineNo).clear();
    }
}
