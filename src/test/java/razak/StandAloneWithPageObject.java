package razak;

import io.github.bonigarcia.wdm.WebDriverManager;
import junit.framework.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import razak.pageObjects.LandingPage;
import razak.pageObjects.ProductCatalogPage;

import java.time.Duration;
import java.util.List;

public class StandAloneWithPageObject
{
    private static String password = "razzy@gmail.comY$7";
    private static String email = "razzy@gmail.com";
    public static void main(String[] args) {
        String productName = "ZARA COAT 3";
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        LandingPage landingPage = new LandingPage(driver);
        landingPage.goTo();
        landingPage.logInApplication(email, password);
        ProductCatalogPage productCatalogPage = new ProductCatalogPage(driver);
        List<WebElement> products = productCatalogPage.getProductList();
        productCatalogPage.addProductToCart(productName);


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("#ng-animating")));

        // View cart
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[routerlink*='cart']")));
        driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
        System.out.println(driver.findElement(By.cssSelector("[routerlink*='cart']")).getText());

        // Check if product in indeed in the cart
        List<WebElement> cartProducts =  driver.findElements(By.cssSelector(".cartSection h3"));
        Boolean match = cartProducts.stream().anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(productName));
        Assert.assertTrue(match);

        // make an order
        driver.findElement(By.cssSelector(".totalRow button")).click();

        Actions action = new Actions(driver);
        action.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "Ghana")
                .build().perform();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-item"))).click();
        driver.findElement(By.cssSelector("a.btnn")).click();

        String orderSuccessMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
        System.out.println(orderSuccessMessage);
        Assert.assertTrue(orderSuccessMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

    }
}
