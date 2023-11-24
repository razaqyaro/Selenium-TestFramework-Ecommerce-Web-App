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

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class StandAloneTest
{
    private static String password = "razzy@gmail.comY$7";
    private static String email = "razzy@gmail.com";
    public static void main(String[] args)
    {
        String productName = "ZARA COAT 3";
        WebDriverManager.chromedriver().setup();
//        System.setProperty("webdriver.chrome.driver", "C:\\browserDrivers\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.get("https://rahulshettyacademy.com/client");
        driver.findElement(By.id("userEmail")).sendKeys(email);
        driver.findElement(By.id("userPassword")).sendKeys(password);
        driver.findElement(By.id("login")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
        List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
       WebElement foundProduct = products.stream().filter(product ->
                product.findElement(By.cssSelector("b")).getText().equals(productName))
                .findFirst().orElse(null);
       foundProduct.findElement(By.cssSelector(".card button:last-of-type")).click();

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
