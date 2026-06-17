package tests;

import org.testng.annotations.Test;

import base.BaseTest;
import pages.ProductManagementRoboPage;
import utils.LoginHelper;

public class ProductManagementRoboTest extends BaseTest {

    @Test
    public void createProductWithRandomAutoTags() {

        LoginHelper.loginAsSuperAdmin(driver);

        ProductManagementRoboPage page =
                new ProductManagementRoboPage(driver);

        page.openProductManagement();

       
        page.fillProduct("Test", "10", "15");

        page.fillStock("20");

       
        page.autoGenerateTagsAndSave(11);
    }
}
