package pages;

import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class CartRegisterRoboPage {

    WebDriver driver;
    WebDriverWait wait;

    public CartRegisterRoboPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    By cartPrefix = By.id("cartPrefix");
    By cartNumber = By.id("cartNumber");
    By machineNo  = By.id("machNo");
    By submitBtn  = By.xpath("//button[normalize-space()='Submit']");

    // SweetAlert
    By popupBox     = By.cssSelector("div.swal2-popup");
    By popupMessage = By.cssSelector("div.swal2-html-container");
    By okButton     = By.xpath("//button[normalize-space()='OK']");

    /** Submit cart and return popup status */
    public PopupStatus createCartAndGetStatus(
            String prefix, String number, String machine) {

        fill(cartPrefix, prefix);
        fill(cartNumber, number);
        fill(machineNo, machine);

        wait.until(driver -> driver.findElement(submitBtn).isEnabled());
        driver.findElement(submitBtn).click();

        return handlePopup();
    }

    private void fill(By locator, String value) {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(locator));
        el.sendKeys(Keys.chord(Keys.CONTROL, "a"), value, Keys.TAB);
    }

    /** CORE POPUP HANDLER */
    private PopupStatus handlePopup() {

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(popupBox));

            String msg = "";
            try {
                msg = driver.findElement(popupMessage).getText().toLowerCase();
            } catch (Exception ignored) {}

            System.out.println("Popup message: " + msg);

            wait.until(ExpectedConditions.elementToBeClickable(okButton)).click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(popupBox));

            clearFields();

            if (msg.contains("success")) {
                return PopupStatus.SUCCESS;
            }
            if (msg.contains("exist") || msg.contains("duplicate")) {
                return PopupStatus.DUPLICATE;
            }
            if (msg.contains("unexpected") || msg.contains("oops")) {
                return PopupStatus.ERROR;
            }

            return PopupStatus.UNKNOWN;

        } catch (TimeoutException e) {
            System.out.println("No popup detected");
            return PopupStatus.NO_POPUP;
        }
    }

    private void clearFields() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartPrefix));
        driver.findElement(cartPrefix).clear();
        driver.findElement(cartNumber).clear();
        driver.findElement(machineNo).clear();
    }

    /* Popup result types */
    public enum PopupStatus {
        SUCCESS,
        DUPLICATE,
        ERROR,
        UNKNOWN,
        NO_POPUP
    }
}
