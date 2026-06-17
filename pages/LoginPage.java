package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    WebDriver driver;
    WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // Angular-stable locators
    By username = By.xpath("//input[@formcontrolname='Username']");
    By password = By.xpath("//input[@formcontrolname='Password']");
    By loginBtn = By.xpath("//button[@type='submit']");
    By loader = By.cssSelector("div.app-loading");

    public void login(String user, String pass) {

        // wait for username field
        wait.until(ExpectedConditions.visibilityOfElementLocated(username))
            .sendKeys(user);

        wait.until(ExpectedConditions.visibilityOfElementLocated(password))
            .sendKeys(pass);

        // wait until loader disappears
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loader));

        // wait until login button is clickable
        WebElement loginButton =
                wait.until(ExpectedConditions.elementToBeClickable(loginBtn));

        loginButton.click();
    }
}
