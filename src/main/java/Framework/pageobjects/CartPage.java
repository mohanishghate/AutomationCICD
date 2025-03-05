package Framework.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;



import Framework.AbstractComponents.AbstractComponents;

public class CartPage extends AbstractComponents{

	WebDriver driver;
	//driver.findElement(By.cssSelector(".totalRow button")).click();
	@FindBy(css=".totalRow button")
	WebElement checkoutEle;
	
	//List<WebElement> cartProducts=driver.findElements(By.cssSelector(".cartSection h3"));
	@FindBy(css=".cartSection h3")
	private List<WebElement> cartProducts;
	
	public CartPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	public Boolean verifyProductDisplay(String productName)
	{
		Boolean match=cartProducts.stream().anyMatch(product->product.getText().equalsIgnoreCase(productName));
		return match;
	}
	
	public CheckOutPage goToCheckOut()
	{
		checkoutEle.click();
		return new CheckOutPage(driver);
	}
}

