package Framework.tests;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.sun.net.httpserver.Authenticator.Retry;

import Framework.AbstractComponents.AbstractComponents;
import Framework.TestComponent.BaseTest;
import Framework.pageobjects.CartPage;
import Framework.pageobjects.CheckOutPage;
import Framework.pageobjects.ConfirmationPage;
import Framework.pageobjects.LandingPage;
import Framework.pageobjects.ProductCatalogue;
import io.github.bonigarcia.wdm.WebDriverManager;

public class ErrorValidationTest extends BaseTest{

		@Test(groups= {"ErrorHandling"}, retryAnalyzer=Framework.TestComponent.Retry.class)
		public void loginErrorValidation() throws IOException, InterruptedException
		{
			
		landingPage.loginApplication("mghate@abcd.com", "Mohanish@123");
		System.out.println(landingPage.getErrorMessage());
		Assert.assertEquals("Incorrect email password.", landingPage.getErrorMessage());
		}
		@Test
		public void productErrorValidation() throws IOException, InterruptedException
		{
			
			String productName="QWERTY";
			ProductCatalogue productCatalogue=landingPage.loginApplication("mghate@abc.com", "Mohanish@123");
			List<WebElement> products=productCatalogue.getProductList();
			productCatalogue.addProductToCart(productName);
			CartPage cartPage=productCatalogue.goToCartPage();
			Boolean match=cartPage.verifyProductDisplay("QWERTY3");
			Assert.assertFalse(match);
			
		}

}
