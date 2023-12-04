package razak.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import razak.abstractComponents.AbstractComponent;

import java.util.List;

public class OrderPage extends AbstractComponent
{
    WebDriver driver;

    public OrderPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".totalRow button")
    WebElement checkOutLocator;

    @FindBy(css = "tr td:nth-child(3)")
    private List<WebElement> orderTitles;

    public Boolean verifyOrderDisplay(String productName)
    {
        return orderTitles.stream().anyMatch(order -> order.getText().equalsIgnoreCase(productName));
    }

    public void checkout()
    {
        checkOutLocator.click();
    }
}
