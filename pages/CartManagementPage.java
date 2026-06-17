package pages;

import java.time.Duration;
import java.util.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class CartManagementPage {

    WebDriver driver;
    WebDriverWait wait;

    public CartManagementPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(40));
    }

    By cartBoxes = By.cssSelector("div.transactions-box");

  
    public List<String[]> getAllCartDetails() {

        List<String[]> carts = new ArrayList<>();

        wait.until(ExpectedConditions.urlContains("cartManagement"));
        wait.until(ExpectedConditions.presenceOfElementLocated(cartBoxes));

        List<WebElement> boxes = driver.findElements(cartBoxes);
        System.out.println("Cards found = " + boxes.size());

        for (WebElement box : boxes) {
            try {
                WebElement details =
                    box.findElement(By.cssSelector("div.transaction-details"));

                String text = details.getText();

                if (!text.contains("Cart No") || !text.contains("Machine No")) {
                    throw new RuntimeException("Invalid cart block");
                }

                String cartNo = text
                        .split("Cart No:")[1]
                        .split("\n")[0]
                        .trim();

                String machineNo = text
                        .split("Machine No:")[1]
                        .split("\n")[0]
                        .trim();

                String qrUrl = "N/A";
                List<WebElement> qr =
                    box.findElements(By.cssSelector("div.transaction-qrcode qrcode"));

                if (!qr.isEmpty()) {
                    qrUrl = qr.get(0).getAttribute("title");
                }

                carts.add(new String[]{ cartNo, machineNo, qrUrl });
                System.out.println("✅ " + cartNo + " | " + machineNo);

            } catch (Exception e) {
                System.out.println("Skipped broken cart");
            }
        }
        return carts;
    }

    
    public List<String[]> getAllPagesCartDetails() {

        List<String[]> allCarts = new ArrayList<>();

        while (true) {

            // Capture current page number
            String currentPage = driver.findElement(
                By.cssSelector("li.page-item.active")
            ).getText().trim();

            // Collect carts from current page
            allCarts.addAll(getAllCartDetails());

            try {
                WebElement nextBtn = driver.findElement(
                    By.xpath("//a[contains(@class,'page-link') and normalize-space()='Next']")
                );

                // If Next is disabled → stop
                if (nextBtn.findElement(By.xpath(".."))
                        .getAttribute("class").contains("disabled")) {
                    break;
                }

                // Click Next (Angular-safe)
                ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();", nextBtn);

                // WAIT UNTIL PAGE NUMBER CHANGES
                wait.until(driver ->
                    !driver.findElement(
                        By.cssSelector("li.page-item.active")
                    ).getText().trim().equals(currentPage)
                );

            } catch (Exception e) {
                break;
            }
        }

        return allCarts;
    }

}
