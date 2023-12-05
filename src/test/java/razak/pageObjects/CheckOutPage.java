package razak.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import razak.abstractComponents.AbstractComponent;

public class CheckOutPage extends AbstractComponent
{
 WebDriver driver;
    public CheckOutPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".btnn.action__submit.ng-star-inserted")
    WebElement submit;

    @FindBy(css = "[placeholder = 'Select Country']")
    WebElement country;

    @FindBy(css = ".ta-item")
    WebElement selectCountry;

    By countryLocator = By.cssSelector(".ta-item");
    public void selectCountry(String countryName)
    {
        Actions a = new Actions(driver);
        a.sendKeys(country, countryName).build().perform();
        waitForElementToAppear(countryLocator);
        selectCountry.click();
    }
    public ConfrimationPage submitOrder()
    {
        submit.click();
        return new ConfrimationPage(driver);
    }
}
