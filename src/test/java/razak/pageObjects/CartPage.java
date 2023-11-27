package razak.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import razak.abstractComponents.AbstractComponent;

import java.util.List;

public class CartPage extends AbstractComponent
{
    WebDriver driver;
    public CartPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".totalRow button")
    WebElement checkOutLocator;

    @FindBy(css = ".cartSection h3")
    private List<WebElement> productTitles;

    public Boolean verifyProductDisplay(String productName)
    {
        return productTitles.stream().anyMatch(product -> product.getText().equalsIgnoreCase(productName));
    }

    public void checkout()
    {
        checkOutLocator.click();
    }
}
