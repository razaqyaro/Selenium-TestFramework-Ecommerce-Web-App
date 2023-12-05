package stepDefinition;

import TestComponents.BaseTest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.Assert;
import org.openqa.selenium.WebElement;
import razak.pageObjects.*;

import java.io.IOException;
import java.util.List;

public class StepDefinitionImpl extends BaseTest
{
    public LandingPage landingPage;
  //  public ProductCatalogPage productCatalogPage;
    public ConfrimationPage confirmationPage;
    @Given("I landed on Ecommerce Page")
    public void I_landed_on_Ecommerce_Page() throws IOException
    {
       landingPage = launchApplication();
    }
    @Given("^Logged in with username (.+) and password (.+)$")
    public void logged_in_username_password(String email, String password)
    {
        landingPage.logInApplication(email, password);
    }

    @When("^I add product (.+) to Cart$")
    public void I_Add_Product_To_Cart(String productName)
    {
        ProductCatalogPage productCatalogPage = new ProductCatalogPage(driver);
        List<WebElement> products = productCatalogPage.getProductList();
        productCatalogPage.addProductToCart(productName);
    }

    @And("^Checkout (.+) and submit the order$")
    public void checkoutAndSubmitTheOrder(String productName)
    {
        ProductCatalogPage  productCatalogPage = new  ProductCatalogPage(driver);
       CartPage cartPage =  productCatalogPage.goToCartPage();
        Boolean match = cartPage.verifyProductDisplay(productName);
        Assert.assertTrue(match);
        cartPage.checkout();
        CheckOutPage checkOutPage = new CheckOutPage(driver);
        checkOutPage.selectCountry("Ghana");
        checkOutPage.submitOrder();
        confirmationPage = checkOutPage.submitOrder();
    }

    @Then("{string} message is displayed on the ConfirmationPage")
    public void message_displayed_confirmationPage(String message)
    {
        String confirmMessage = confirmationPage.getConfirmationMessage();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase(message));

    }
//    @Then("{string} message")
//    public void message(String message) {
//        String confirmMessage = confirmationPage.getConfirmationMessage();
//        Assert.assertTrue(confirmMessage.equalsIgnoreCase(message));
//    }
}
