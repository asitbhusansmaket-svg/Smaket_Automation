package tests;

import java.util.List;
import java.util.Map;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.ProductManagementPage;
import utils.ExcelUtils;
import utils.LoginHelper;

public class ProductManagementTest extends BaseTest {

    @Test
    public void addProductUsingExcel() {

        List<Map<String, String>> products =
                ExcelUtils.readProductManagementData(
                        "C:\\Users\\Administrator\\Downloads\\Audit export Warehouse Test.xlsx",
                        "Sheet1"
                );

        LoginHelper.loginAsSuperAdmin(driver);

        ProductManagementPage page =
                new ProductManagementPage(driver);

        for (Map<String, String> data : products) {

            page.openProductManagement();

//            page.fillProduct(
//                    data.get("Product Name"),
//                    data.get("Purchase Base Price"),
//                    data.get("Sales Base Price")
//            );"C:\Users\Administrator\Downloads\Audit export Warehouse Test.xlsx"
            page.fillProduct(
                    data.get("Product Name"), 
                    data.get("Purchase Base Price"),
                    data.get("Sales Base Price")
            );

            page.fillStock(
                    data.get("Opening Stock")
            );
  
           /* page.addTagAndSave(
            		data.get("Serial no")
            		data.get("Tag ID"),
            		data.get("Warehouse")
            		);*/
            page.addTagAndSave(
            		data.get("Serial no"),
            		data.get("Warehouse")
            		);
            
            page.generaldetails(data.get("Product Code"),
            		data.get("Model No"),
            		data.get("Description"));
            
            page.saveProduct();

            
        }
    }
}
