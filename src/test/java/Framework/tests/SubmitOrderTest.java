package Framework.tests;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Framework.AbstractComponents.AbstractComponents;
import Framework.TestComponent.BaseTest;
import Framework.pageobjects.CartPage;
import Framework.pageobjects.CheckOutPage;
import Framework.pageobjects.ConfirmationPage;
import Framework.pageobjects.LandingPage;
import Framework.pageobjects.OrderPage;
import Framework.pageobjects.ProductCatalogue;
import io.github.bonigarcia.wdm.WebDriverManager;

public class SubmitOrderTest extends BaseTest{
	String productName="QWERTY";
		@Test(dataProvider="getData",groups= {"Purchase"})
		public void submitOrder(HashMap<String,String> input) throws IOException, InterruptedException
		{
			ProductCatalogue productCatalogue=landingPage.loginApplication(input.get("email"), input.get("password"));
			List<WebElement> products=productCatalogue.getProductList();
			productCatalogue.addProductToCart(input.get("product"));
			CartPage cartPage=productCatalogue.goToCartPage();
			Boolean match=cartPage.verifyProductDisplay(input.get("product"));
			Assert.assertTrue(match);
			CheckOutPage checkoutPage=cartPage.goToCheckOut();
			checkoutPage.selectCountry("india");
			ConfirmationPage confirmationPage=checkoutPage.submitOrder();
			String confirmMessage=confirmationPage.getConfirmationMessage();
			Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		}
		@Test(dependsOnMethods = {"submitOrder"})
		public void OrderHistoryTest()
		{
			ProductCatalogue productCatalogue=landingPage.loginApplication("mghate@abc.com", "Mohanish@123");
			OrderPage ordersPage= productCatalogue.goToOrdersPage();
			Assert.assertTrue(ordersPage.verifyOrderDisplay(productName));
		}
		
		
		@DataProvider
		public Object[][] getData() throws IOException
		{
			
			List<HashMap<String,String>> data=getJsonDataToMap(System.getProperty("user.dir")+"\\src\\test\\java\\Framework\\data\\PurchaseOrder.json");
			return new Object[][] {{data.get(0)},{data.get(1)}};
			
		}
		
		/*
		 * @DataProvider public getData() 
		 * {
		 *  return new Object[][] {{"mghate@abc.com","Mohanish@123","QWERTY"},{"mohanghate@abc.com","Mohan@123","IPHONE 13 PRO"}}; 
		 * }
		 */
		
		/*HashMap<String,String> map=new HashMap<String,String>();
		map.put("email","mghate@abc.com");
		map.put("password", "Mohanish@123");
		map.put("product", "QWERTY");
		
		
		HashMap<String,String> map1=new HashMap<String,String>();
		map1.put("email","mohanghate@abc.com");
		map1.put("password", "Mohan@123");
		map1.put("product", "IPHONE 13 PRO");*/
}
