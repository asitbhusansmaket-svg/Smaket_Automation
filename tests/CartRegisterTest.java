package tests;

import java.util.List;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.CartRegisterPage;
import pages.UtilitiesPage;
import utils.ExcelUtils;
import utils.LoginHelper;

public class CartRegisterTest extends BaseTest {

    @Test
    public void createCartFromExcel() {

        LoginHelper.loginAsSuperAdmin(driver);

        UtilitiesPage utilities = new UtilitiesPage(driver);
        utilities.goToRegisterCart();

        String excelPath = "D:/Eclipse/DataUpload/Cart.xlsx";
        List<String[]> data =
                ExcelUtils.readCartData(excelPath, "Sheet1");

        System.out.println("Rows read = " + data.size());

        CartRegisterPage cart = new CartRegisterPage(driver);

        for (String[] row : data) {
            cart.createCart(row[0], row[1], row[2]);
        }
    }
}
