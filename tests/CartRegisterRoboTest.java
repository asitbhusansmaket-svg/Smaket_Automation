package tests;

import org.testng.annotations.Test;

import base.BaseTest;
import pages.CartRegisterRoboPage;
import pages.CartRegisterRoboPage.PopupStatus;
import pages.UtilitiesPage;
import utils.LoginHelper;
import utils.RandomDataUtil;

public class CartRegisterRoboTest extends BaseTest {

    @Test
    public void createRandomCartsWithAllPopupHandling() {

        int cartCount = 5;
        int maxRetry  = 3;

        LoginHelper.loginAsSuperAdmin(driver);

        UtilitiesPage utilities = new UtilitiesPage(driver);
        utilities.goToRegisterCart();

        CartRegisterRoboPage page = new CartRegisterRoboPage(driver);

        for (int i = 1; i <= cartCount; i++) {

            boolean done = false;
            int attempt = 0;

            while (!done && attempt < maxRetry) {
                attempt++;

                String prefix  = RandomDataUtil.cartPrefix();
                String number  = RandomDataUtil.randomCartNumber();
                String machine = RandomDataUtil.randomMachineNumber();

                System.out.println("Attempt " + attempt +
                        " → " + prefix + "-" + number);

                PopupStatus status =
                        page.createCartAndGetStatus(prefix, number, machine);

                switch (status) {

                    case SUCCESS:
                        System.out.println("Cart created");
                        done = true;
                        break;

                    case DUPLICATE:
                        System.out.println("Duplicate → retrying");
                        break;

                    case ERROR:
                        System.out.println("System error → retrying");
                        break;

                    default:
                        System.out.println("Unexpected state → skipping");
                        done = true;
                        break;
                }
            }

            if (!done) {
                System.out.println("Cart creation failed after retries");
            }
        }
    }
}
