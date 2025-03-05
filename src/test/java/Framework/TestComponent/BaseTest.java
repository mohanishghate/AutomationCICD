package Framework.TestComponent;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import Framework.pageobjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

	public WebDriver driver;
	public LandingPage landingPage;
	public WebDriver initializeDriver() throws IOException
	{
		Properties prop= new Properties();
		FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\Framework\\resources\\GlobalData.properties");
		prop.load(fis);
		String browserName=System.getProperty("browser")!=null? System.getProperty("browser"):prop.getProperty("browser");
		//prop.getProperty("browser");
		
		if(browserName.contains("chrome"))
		{
			
		ChromeOptions options=new ChromeOptions();
		
		WebDriverManager.chromedriver().setup();
		if(browserName.contains("headless"))
		{
			options.addArguments("headless");
		}
		driver = new ChromeDriver(options);
		driver.manage().window().setSize(new Dimension(1920, 1200));
		}
		
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		return driver;
		}
	
	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException
	{
		//reading Json to string
		String jsonContent= FileUtils.readFileToString(new File(filePath),StandardCharsets.UTF_8);
		
		//String to hashmap->dependency required jackson databind
		ObjectMapper mapper=new ObjectMapper();
		List<HashMap<String,String>> data=mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>(){
		});
		//readValue->read the string and convert to Hashmap
		//read value->1st arg string which need to convert into hashmap
		//2nd argument tells how you want to convert it
		//After creating hashmap it will create a list and give us back
		
		return data;
	}
	public String getScreenShot(String testCaseName,WebDriver driver) throws IOException
	{
		TakesScreenshot ts=(TakesScreenshot)driver;
		File source= ts.getScreenshotAs(OutputType.FILE);
		File file=new File(System.getProperty("user.dir")+"//reports//" + testCaseName +" .png");
		FileUtils.copyFile(source,file);
		return System.getProperty("user.dir")+"//reports//" + testCaseName +" .png";
		
	}
	
	
	
	
	@BeforeMethod(alwaysRun=true)
	public LandingPage launchApplication() throws IOException
	{
		driver=initializeDriver();
		landingPage= new LandingPage(driver);
		landingPage.goTo();
		return landingPage;
	}
}