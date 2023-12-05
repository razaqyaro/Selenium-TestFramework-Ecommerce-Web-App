package razak;

import TestComponents.BaseTest;
import TestComponents.Retry;
import junit.framework.Assert;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import razak.pageObjects.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class StandAloneWithPageObject extends BaseTest
{

    private static String password2 = "razy@gmail.comY$7";
    private static String email2 = "razzy@mail.com";
    @Test(dataProvider = "getData", groups = {"Purchase"})
    public void shopProductTest(String email, String password, String productName) throws IOException
    {
        landingPage.logInApplication(email, password);
        ProductCatalogPage productCatalogPage = new ProductCatalogPage(driver);
        List<WebElement> products = productCatalogPage.getProductList();
        productCatalogPage.addProductToCart(productName);
        productCatalogPage.goToCartPage();
        CartPage cartPage = new CartPage(driver);
        Boolean match = cartPage.verifyProductDisplay(productName);
        Assert.assertTrue(match);
        cartPage.checkout();
        CheckOutPage checkOutPage = new CheckOutPage(driver);
        checkOutPage.selectCountry("Ghana");
        checkOutPage.submitOrder();
        String confirmationMessage = new ConfrimationPage(driver).getConfirmationMessage();
        Assert.assertTrue(confirmationMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

    }
    @Test(dataProvider = "getDataFromJson")
    public void submitTest(HashMap<String, String> logInCredentials) throws IOException
    {
        landingPage.logInApplication(logInCredentials.get("email"),logInCredentials.get("password"));
        ProductCatalogPage productCatalogPage = new ProductCatalogPage(driver);
        List<WebElement> products = productCatalogPage.getProductList();
        productCatalogPage.addProductToCart(logInCredentials.get("product"));
        productCatalogPage.goToCartPage();
        CartPage cartPage = new CartPage(driver);
        Boolean match = cartPage.verifyProductDisplay(logInCredentials.get("product"));
        Assert.assertTrue(match);
        cartPage.checkout();
        CheckOutPage checkOutPage = new CheckOutPage(driver);
        checkOutPage.selectCountry("Ghana");
        checkOutPage.submitOrder();
        String confirmationMessage = new ConfrimationPage(driver).getConfirmationMessage();
        Assert.assertTrue(confirmationMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

    }
    @Test(retryAnalyzer = Retry.class)
    public void submitOrder() throws IOException
    {
        landingPage.logInApplication(email2, password2);
        String expectedErrorMessage = landingPage.getErrorMessage();
        Assert.assertEquals(expectedErrorMessage, "Incorrect email or password.");

    }

    @Test(dependsOnMethods = {"shopProductTest"}, dataProvider = "getData")
    public void orderHistoryTest(String email, String password, String productName)
    {
        landingPage.logInApplication(email, password);
        OrderPage orderPage = new OrderPage(driver);
        Assert.assertTrue(orderPage.verifyOrderDisplay(productName));
    }
    @DataProvider
    public Object[][] getData()
    {
        return new Object[][] {{"razzy@gmail.com","razzy@gmail.comY$7","ZARA COAT 3" }, {"razzy@gmail.com","razzy@gmail.comY$7","ZARA COAT 3"}};
    }

    @DataProvider
    public Object[][] getCredentials()
    {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("email", "razzy@gmail.com");
        map.put("password", "razzy@gmail.comY$7");
        map.put("product", "ZARA COAT 3");

        HashMap<String, String> map1 = new HashMap<String, String>();
        map1.put("email", "shetty@gmail.com");
        map1.put("password", "Iamking@000");
        map1.put("product", "ADIDAS ORIGINAL");
        return  new Object[][]{{map}, {map1}};
    }

    @DataProvider
    public Object[][] getDataFromJson() throws IOException {
        List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+"\\src\\test\\java\\data\\PurchaseOrder.json");
        return new Object[][] {{data.get(0)}, {data.get(1)}};
    }




}
