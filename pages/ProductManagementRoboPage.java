package pages;

import java.time.Duration;
import java.util.UUID;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class ProductManagementRoboPage {

    WebDriver driver;
    WebDriverWait wait;

    public ProductManagementRoboPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    //LOCATORS

    By productManagementMenu =
        By.xpath("//a[@title='Product Management' or .//p[text()='Product Management']]");

    By addButton =
        By.xpath("//button[normalize-space()='ADD']");

    By productName =
        By.xpath("//input[@placeholder='Enter Product Name']");

    By purchasePrice =
        By.xpath("//input[@placeholder='Basic Price']");

    By salesPrice =
        By.xpath("//input[@placeholder='Sell Base Price']");

    By stockDetailsTab =
        By.xpath("//a[normalize-space()='Stock Details']");

    By openingStock =
        By.xpath("//input[@name='BatchwiseOpeningStock']");

    By slNoTagBtn =
        By.xpath("//*[contains(text(),'Sl No/TAG No')]");

    By tagIdInput =
        By.xpath("//input[@placeholder='Enter Tag ID']");

    By tagAddBtn =
        By.xpath("//button[normalize-space()='Add']");

    By modalVisible =
        By.cssSelector(".modal.show");

    By closeTagModalBtn =
        By.xpath("//div[contains(@class,'modal-content add-po all-modal-content')]//div[contains(@class,'modal-header add-po-header all-modal-header')]//div//button[contains(@type,'button')][normalize-space()='×']");

    By saveBtn =
        By.xpath("//button[contains(@class,'pull-right')][normalize-space()='Save']");

    By successPopupText =
        By.xpath("//*[contains(text(),'data submitted successfully')]");

    By successOkBtn =
        By.xpath("//button[normalize-space()='OK']");

  

    public void openProductManagement() {
        wait.until(ExpectedConditions.elementToBeClickable(productManagementMenu)).click();
        wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();
    }

    public void fillProduct(String name, String purchase, String sales) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(productName)).sendKeys(name);
        driver.findElement(purchasePrice).sendKeys(purchase);
        driver.findElement(salesPrice).sendKeys(sales);
    }

    public void fillStock(String stock) {
        wait.until(ExpectedConditions.elementToBeClickable(stockDetailsTab)).click();
        WebElement opening =
                wait.until(ExpectedConditions.visibilityOfElementLocated(openingStock));
        opening.clear();
        opening.sendKeys(stock);
    }


    public void autoGenerateTagsAndSave(int tagCount) {

        JavascriptExecutor js = (JavascriptExecutor) driver;

        //OPEN TAG MODAL
        wait.until(ExpectedConditions.elementToBeClickable(slNoTagBtn)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(modalVisible));

        // AUTO GENERATE TAGS 
        for (int i = 1; i <= tagCount; i++) {

            String autoTag =
                    "TAG-" + UUID.randomUUID().toString().substring(0, 8);

            WebElement tagInput =
                    wait.until(ExpectedConditions.visibilityOfElementLocated(tagIdInput));
            tagInput.clear();
            tagInput.sendKeys(autoTag);

            wait.until(ExpectedConditions.elementToBeClickable(tagAddBtn)).click();

            // SAME verification as Excel module
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//td[normalize-space()='" + autoTag + "']")));

            try { Thread.sleep(300); } catch (Exception ignored) {}
        }

        // CLOSE TAG MODAL
        wait.until(ExpectedConditions.elementToBeClickable(closeTagModalBtn)).click();

        // SAVE PRODUCT
        WebElement save =
                wait.until(ExpectedConditions.presenceOfElementLocated(saveBtn));
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", save);
        try { Thread.sleep(500); } catch (Exception ignored) {}
        js.executeScript("arguments[0].click();", save);

        
        handleSuccessPopup();

       
        wait.until(ExpectedConditions.elementToBeClickable(addButton));
    }

    // HELPER

    private void handleSuccessPopup() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(10));
            shortWait.until(ExpectedConditions.visibilityOfElementLocated(successPopupText));
            shortWait.until(ExpectedConditions.elementToBeClickable(successOkBtn)).click();
            shortWait.until(ExpectedConditions.invisibilityOfElementLocated(successPopupText));
        } catch (TimeoutException e) {
            
        }
    }
}
