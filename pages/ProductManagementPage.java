package pages;

import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class ProductManagementPage {

    WebDriver driver;
    WebDriverWait wait;

    public ProductManagementPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    // ================= LOCATORS =================

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

    By wearhouse=By.xpath("//div[@class='form-control warehouse-trigger text-wrap py-2']");

	By warehouseSearch =
	    By.xpath("//input[@placeholder='Search warehouse...']");
	
    By tagAddBtn =
        By.xpath("//button[normalize-space()='Add']");

    By modalVisible =
        By.cssSelector(".modal.show");

    By closeTagModalBtn =
        By.xpath("//div[contains(@class,'modal-content add-po all-modal-content')]//div[contains(@class,'modal-header add-po-header all-modal-header')]//div//button[contains(@type,'button')][normalize-space()='×']");

   
    By general = By.xpath("//a[@id='vert-tabs-general-tab']");
    By productCode =
    	    By.xpath("//input[@placeholder='Enter Product Code']");

   
    	By modelNo =
    	    By.xpath("//input[@placeholder='Enter Model No /Catlog ']");
    	 By saveBtn =
    		        By.xpath("//button[contains(@class,'pull-right')][normalize-space()='Save']");

    		    // ✅ Success popup
    		    By successPopupText =
    		        By.xpath("//*[contains(text(),'data submitted successfully')]");

    		    By successOkBtn =
    		        By.xpath("//button[normalize-space()='OK']");

    	

    // ================= ACTION METHODS =================

    public void openProductManagement() {
        wait.until(ExpectedConditions.elementToBeClickable(productManagementMenu)).click();
        wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();
    }

//    public void fillProduct(String name, String purchase, String sales) {
//        wait.until(ExpectedConditions.visibilityOfElementLocated(productName)).sendKeys(name);
//        driver.findElement(purchasePrice).sendKeys(purchase);
//        driver.findElement(salesPrice).sendKeys(sales);
//    }
    public void fillProduct(
            String name,
            String purchase,
            String sales) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(productName))
                .sendKeys(name);

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

    /**
     * Adds tags from comma-separated Excel value,
     * closes tag modal, saves product,
     * handles success popup,
     * prepares page for next Excel row
     */
    
    public void addTagAndSave(String tagIds, String search) {

        if (tagIds == null || tagIds.trim().isEmpty()) {
            throw new RuntimeException("❌ Tag ID is empty in Excel");
        }

        JavascriptExecutor js = (JavascriptExecutor) driver;

        // -------- OPEN TAG MODAL --------
        wait.until(ExpectedConditions.elementToBeClickable(slNoTagBtn)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(modalVisible));

        String[] tags = tagIds.split(",");

        for (String tag : tags) {

            String cleanTag = tag.trim();
            if (cleanTag.isEmpty()) continue;

            WebElement tagInput =
                    wait.until(ExpectedConditions.visibilityOfElementLocated(tagIdInput));
//            tagInput.clear();
//            tagInput.sendKeys(cleanTag);
//
//            wait.until(ExpectedConditions.elementToBeClickable(tagAddBtn)).click();
            tagInput.clear();
            tagInput.sendKeys(cleanTag);


            wait.until(ExpectedConditions.elementToBeClickable(tagAddBtn)).click();

            // Wait until tag appears in table
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//td[normalize-space()='" + cleanTag + "']")));

            try { Thread.sleep(300); } catch (Exception ignored) {}
        }
//        wait.until(ExpectedConditions.elementToBeClickable(wearhouse)).click();
//        WebElement wsearch=wait.until(ExpectedConditions.elementToBeClickable(warehouseSearch));
//        wsearch.click();
//        wsearch.sendKeys(search);
//        wait.until(ExpectedConditions.elementToBeClickable(closeTagModalBtn)).click();
        wait.until(ExpectedConditions.elementToBeClickable(wearhouse)).click();
        
        WebElement wsearch = wait.until(ExpectedConditions.elementToBeClickable(warehouseSearch));
        wsearch.click();
        wsearch.sendKeys(search);
         
        // NEW: click the actual matching warehouse from the filtered list
        By warehouseItem = By.xpath(
            "//button[contains(@class,'warehouse-item')][.//span[normalize-space()='" + search + "']]"
        );
         
        WebElement item = wait.until(ExpectedConditions.elementToBeClickable(warehouseItem));
        item.click();
        
        
        // -------- CLOSE TAG MODAL --------
        wait.until(ExpectedConditions.elementToBeClickable(closeTagModalBtn)).click();
        
       
        

        // -------- SAVE PRODUCT (JS CLICK) --------
        WebElement save =
                wait.until(ExpectedConditions.presenceOfElementLocated(saveBtn));
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", save);
        try { Thread.sleep(500); } catch (Exception ignored) {}
        js.executeScript("arguments[0].click();", save);

        // -------- HANDLE SUCCESS POPUP --------
        handleSuccessPopup();

        // -------- READY FOR NEXT EXCEL ROW --------
        wait.until(ExpectedConditions.elementToBeClickable(addButton));
    }
//    public void generaldetails(
//            String code,
//            String model) {
//
//    	wait.until(ExpectedConditions.visibilityOfElementLocated(general)).click();;
//        wait.until(ExpectedConditions.visibilityOfElementLocated(productCode))
//                .sendKeys(code);
//
//        driver.findElement(modelNo).sendKeys(model);
//    }

    // ================= HELPER =================

    private void handleSuccessPopup() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(10));

            shortWait.until(ExpectedConditions.visibilityOfElementLocated(successPopupText));
            shortWait.until(ExpectedConditions.elementToBeClickable(successOkBtn)).click();

            shortWait.until(ExpectedConditions.invisibilityOfElementLocated(successPopupText));

        } catch (TimeoutException e) {
            // Popup not shown – ignore safely
        }
    }
}
