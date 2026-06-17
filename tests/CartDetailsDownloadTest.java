package tests;

import java.util.List;
import org.testng.annotations.Test;
import base.BaseTest;
import pages.CartManagementPage;
import pages.UtilitiesPage;
import utils.ExcelWriterUtils;
import utils.LoginHelper;

public class CartDetailsDownloadTest extends BaseTest {

    @Test
    public void downloadCartDetails() {

        LoginHelper.loginAsSuperAdmin(driver);

        UtilitiesPage utilities = new UtilitiesPage(driver);
        utilities.goToCartManagement();

        CartManagementPage cm = new CartManagementPage(driver);

        // ✅ ALL PAGES
        List<String[]> carts = cm.getAllPagesCartDetails();

        System.out.println("Total carts found = " + carts.size());

        ExcelWriterUtils.writeAllCartDetails(carts);
    }
}
