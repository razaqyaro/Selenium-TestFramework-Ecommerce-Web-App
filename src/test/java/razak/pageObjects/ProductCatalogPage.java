package razak.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import razak.abstractComponents.AbstractComponent;

import java.util.List;

public class ProductCatalogPage extends AbstractComponent
{
    WebDriver driver;

    public ProductCatalogPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".mb-3")
    List<WebElement> products;
    By productsBy = By.cssSelector(".mb-3");
    By ProductToAddToCartLocator = By.cssSelector(".card button:last-of-type");
    By toastMessageLocator = By.cssSelector("#toast-container");
    @FindBy(css ="#ng-animating")
    WebElement spinner ;
    public List<WebElement> getProductList()
    {
        waitForElementToAppear(productsBy);
        return products;
    }

    public WebElement getProductByName(String productName)
    {
        WebElement foundProduct = products.stream().filter(product ->
                        product.findElement(By.cssSelector("b")).getText().equals(productName))
                .findFirst().orElse(null);
        return foundProduct;
    }

    public void addProductToCart(String productName)
    {
        WebElement product = getProductByName(productName);
        product.findElement(ProductToAddToCartLocator).click();
        waitForElementToAppear(toastMessageLocator);
        waitForElementToDisappear(spinner);

    }
}
