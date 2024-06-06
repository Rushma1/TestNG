package variousConcepts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CrmTest {
	WebDriver driver;
	String browser;
	 String urlFromConfig;

	// Login data
	String userName;
	String password;
	
	
	
	
	//Element list- By type

	By USER_NAME_FIELD = By.xpath("//input[@id='user_name']");
	By PASSWORD_FIELD = By.xpath("//input[@id='password']");
	By SIGNIN_BUTTON_FIELD = By.xpath("//button[@id='login_submit']");
	By DASHBOARD_HEADER_FIELD = By.xpath("//strong[text()='Dashboard']");
	By CUSTOMER_MENU_FIELD = By.xpath("//span[text()='Customers']");
	By ADD_CUSTOMER_MENU_FIELD = By.xpath("//span[text()='Add Customer']");
	By ADD_CUSTOMER_HEADER_FIELD = By.xpath("//strong[text()='New Customer']");
	By FULL_NAME_FIELD=By.xpath("//input[@name='name']");
	By COMPANY_DROPEDOWN_FIELD = By.xpath("//select[@name='company_name']");
	By EMAIL_FIELD = By.xpath("//input[@name='email']");
	By COUNTRY_DROPDOWN_FIELD=By.xpath("//select[@name='country']");
	
	By PHONE_NUMBER_FIELD=By.xpath("//input[@id='phone']");
	By ADDRESS_FIELD=By.xpath("//input[@name='address']");
	By CITY_FIELD=By.xpath("//input[@name='city']");
	By ZIPCODE_FIELD= By.xpath("//input[@id='port']");
	By GROUP_DROPDOWN_FIELD= By.xpath("//select[@id='customer_group']");
	By SAVE_BUTTON_FIELD= By.xpath("//button[@id='save_btn']");
	
	//Test or Moke data
		String userNameAlertMsg="Please enter your user name";
		String passwordAlertMsg="Please enter your password";
		String dashboardHeaderText="Dashboard";
		String addCustomerHeaderText="New Customer";
		String fullName="Mr.Selenium";
		String company= "Techfios";
		String email="abc1234@techfios.com";
		String country="United States of America";
		String phone="1234521678";
		String address= "xyz1street";
		String city="jkjkjk";
		String zipcode="454545";
		String group="Java";
		
		
	@BeforeClass
	public void readConfig() {
		//InputStream // BufferedReader // FileReader // Scanner
		try {
			InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");
			Properties prop= new Properties();
			prop.load(input);
			browser= prop.getProperty("browser");
			System.out.println("Browser used: " + browser);
			urlFromConfig=prop.getProperty("url");
			userName= prop.getProperty("userName");
			password= prop.getProperty("password");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@BeforeMethod
	public void setup() {
		if(browser.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
			driver = new ChromeDriver();

		}else if(browser.equalsIgnoreCase("Edge")) {
			System.setProperty("webdriver.edge.driver", "drivers\\msedgedriver.exe");
			driver= new EdgeDriver();
		}else {
			System.out.println("Please use a valid browser!!!");
		}

				
		
		driver.manage().deleteAllCookies();
		driver.get(urlFromConfig);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void testLogin() {
//		String myTitle= driver.getTitle();
//		System.out.println("Title: "+myTitle);
//		String myHandle= driver.getWindowHandle();
//		System.out.println("Handle: "+myHandle);
		
		driver.findElement(USER_NAME_FIELD).sendKeys(userName);
		driver.findElement(PASSWORD_FIELD).sendKeys(password);
		driver.findElement(SIGNIN_BUTTON_FIELD).click();
		Assert.assertEquals(driver.findElement(DASHBOARD_HEADER_FIELD ).getText(), dashboardHeaderText, "Dashboard page not found");
		
		
	}
	
	@Test
	public void testLoginPageAlertMsg() {
		driver.findElement(SIGNIN_BUTTON_FIELD).click();
		String actualUsernameAlertMsg=  driver.switchTo().alert().getText();
		Assert.assertEquals(actualUsernameAlertMsg, userNameAlertMsg, "Do not match!!!!");
		driver.switchTo().alert().accept();
		
		driver.findElement(USER_NAME_FIELD).sendKeys(userName);
		driver.findElement(SIGNIN_BUTTON_FIELD).click();
		String actualpasswordAlertMsg=  driver.switchTo().alert().getText();
		Assert.assertEquals(actualpasswordAlertMsg, passwordAlertMsg, "Do not match!!!");
		driver.switchTo().alert().accept();
	}
	
	@Test
	public void testAddCustomer() {
		testLogin();
		driver.findElement(CUSTOMER_MENU_FIELD).click();
		driver.findElement(ADD_CUSTOMER_MENU_FIELD).click();
		Assert.assertEquals(driver.findElement(ADD_CUSTOMER_HEADER_FIELD).getText(), addCustomerHeaderText, "Add Customer page not available!!!");
		driver.findElement(FULL_NAME_FIELD).sendKeys(fullName + generateRandomNum(999));
		selectFromDropdown(COMPANY_DROPEDOWN_FIELD,company);
		driver.findElement(EMAIL_FIELD).sendKeys(generateRandomNum(99) + email);
		driver.findElement(PHONE_NUMBER_FIELD).sendKeys(phone);
		driver.findElement(ADDRESS_FIELD).sendKeys(address);
		driver.findElement(CITY_FIELD).sendKeys(city);
		driver.findElement(ZIPCODE_FIELD).sendKeys(zipcode);
		
		 
		selectFromDropdown(COUNTRY_DROPDOWN_FIELD,country);
		selectFromDropdown(GROUP_DROPDOWN_FIELD,group);
		driver.findElement(SAVE_BUTTON_FIELD).click();
		
		
		
	}
	
	private int generateRandomNum(int boundaryNum) {
		Random rnd=new Random();
		int randomNum= rnd.nextInt(boundaryNum);
		return randomNum;
	}
	
	private void selectFromDropdown(By field, String visibleText) {
		Select sel = new Select(driver.findElement(field));
		sel.selectByVisibleText(visibleText);
	}

	@AfterMethod
	public void tearDown() {
		driver.close();
		driver.quit();
	}

}