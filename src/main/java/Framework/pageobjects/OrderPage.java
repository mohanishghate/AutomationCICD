package Framework.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Framework.AbstractComponents.AbstractComponents;

public class OrderPage extends AbstractComponents{

	
		WebDriver driver;
		//driver.findElement(By.cssSelector(".totalRow button")).click();
		@FindBy(css=".totalRow button")
		WebElement checkoutEle;
		
		//List<WebElement> cartProducts=driver.findElements(By.cssSelector(".cartSection h3"));
		@FindBy(css="tr td:nth-child(3)")
		private List<WebElement> productNames;
		
		public OrderPage(WebDriver driver) {
			super(driver);
			this.driver=driver;
			PageFactory.initElements(driver, this);
		}
		
		public Boolean verifyOrderDisplay(String productName)
		{
			Boolean match=productNames.stream().anyMatch(product->product.getText().equalsIgnoreCase(productName));
			return match;
		}
		
		
	}


