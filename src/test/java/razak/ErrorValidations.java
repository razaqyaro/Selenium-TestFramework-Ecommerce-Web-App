package razak;

import TestComponents.BaseTest;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import razak.pageObjects.CartPage;
import razak.pageObjects.ProductCatalogPage;
import java.io.IOException;
import java.util.List;

public class ErrorValidations extends BaseTest
{
    private static String password = "razzy@gmail.comY$7";
    private static String email = "razzy@gmail.com";
    private static String password2 = "razy@gmail.comY$7";
    private static String email2 = "razzy@mail.com";
    @Test(groups = {"ErrorValidationsTests"})
    public void logInErrorValidation() throws IOException
    {
        landingPage.logInApplication(email2, password2);
        String expectedErrorMessage = landingPage.getErrorMessage();
        Assert.assertEquals(expectedErrorMessage, "Incorrect email or password.");
    }

    @Test
    public void productErrorValidation() throws IOException
    {
        String productName = "ZARA COAT 3";
        landingPage.logInApplication(email, password);
        ProductCatalogPage productCatalogPage = new ProductCatalogPage(driver);
        List<WebElement> products = productCatalogPage.getProductList();
        productCatalogPage.addProductToCart(productName);
        productCatalogPage.goToCartPage();
        CartPage cartPage = new CartPage(driver);
        Boolean match = cartPage.verifyProductDisplay("ZARA COAT 33");
        Assert.assertFalse(match);
    }

}
