import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import com.google.common.io.Files;

public class NavigationCmd {

	static WebDriver driver = new ChromeDriver();
	
	
	//Checking the Google Page is appearing or not.
	public static boolean GoogleSearchPageValidation(String parentURL)
	{
		
		if(driver.getCurrentUrl().equals(parentURL))
			return true;
		else 
			return false;
		
	}
	
	//Checking the result page is appearing or not.
	public static boolean ResultSearchPageValidation(String childURL)
	{
		
		if(driver.getCurrentUrl().equals(childURL))
			return true;
		else 
			return false;
		
	}
	
	//Validating the navigation of the TAB changing.
	public static boolean navigatingBack(String window1)
	{
		if(driver.getWindowHandle().equals(window1))
		{
			return true;
		}else {
			return false;
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		//implicitly wait for maximum 10 sec.
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));			
		
		//maximisng the window 
		driver.manage().window().maximize();			
		
		//Opening Google.
		driver.get("https://google.com");
		
		
		//clicking on Stay Sign Out
		WebElement frame1 = driver.findElement(By.xpath("//iframe[@name='callout']"));
		driver.switchTo().frame(frame1);
		if(driver.findElements(By.xpath("//button[text()='Stay signed out']")).size()!=0)
		{	
			driver.findElement(By.xpath("//button[text()='Stay signed out']")).click();	
		}
		driver.switchTo().defaultContent();
		
		
		//Storing the parent page URL
		String parentURL = driver.getCurrentUrl();
		
		
		//Passing the values to the search box and clicking search button
		driver.findElement(By.id("APjFqb")).sendKeys("Orange HRM demo");											
		driver.findElement(By.xpath("//div[@class='lJ9FBc']/descendant::input[1]")).click();					
		
		//Storing the child page URL
		String childURL = driver.getCurrentUrl();
		
		//Checking the Google Page is appearing or not.
		driver.navigate().back();
		boolean gpage = GoogleSearchPageValidation(parentURL);
		System.out.println("Is Google Search Page appeared: " + gpage);	
		
		//Checking the result page is appearing or not.
		driver.navigate().forward();
		boolean rpage = ResultSearchPageValidation(childURL);
		System.out.println("Is Result Search Page appeared: " + rpage);
		
		//Open the new link in the new TAB
		driver.switchTo().newWindow(WindowType.TAB);			//creating a new window Tab.
		driver.get("https://www.orangehrm.com/");
		
		
		//collect all the window Ids
		Set<String> winId = driver.getWindowHandles();
		List<String> lis = new ArrayList(winId);
		
		
		//Identifing the window Id's of the two formed window
		String window2 = driver.getWindowHandle();							//geting the ID of the current window Tab
		String window1;
		if(lis.get(0).equals(window2))
		{
			window1 = lis.get(1);
		}else {
			window1 = lis.get(0);
		}
		
		
/*------------------------------------------------Working with 2nd window---------------------------------------------------*/
		
		
		JavascriptExecutor js = (JavascriptExecutor)driver;
		
		//click on Contact Sales button.
		driver.findElement(By.xpath("//div[@id='navbarSupportedContent']/div[2]/ul/li[2]/a/button")).click();
		
		
		//Fill all the data				
		driver.findElement(By.xpath("//input[@id='Form_getForm_FullName']")).sendKeys("Vikram Rathode");
		driver.findElement(By.xpath("//input[@id='Form_getForm_Contact']")).sendKeys("1234567890");
		driver.findElement(By.xpath("//input[@id='Form_getForm_Email']")).sendKeys("rathodeitsolutions.test@test.com ");
		Select slct1 = new Select(driver.findElement(By.xpath("//select[@id='Form_getForm_Country']")));			//Selecting Country
		slct1.selectByVisibleText("India");
		Select slct2 = new Select(driver.findElement(By.xpath("//select[@id='Form_getForm_NoOfEmployees']")));		//Selecting Number Of Employees
		slct2.selectByVisibleText("11 - 15");
		driver.findElement(By.xpath("//input[@id='Form_getForm_JobTitle']")).sendKeys("Fresher");
		
		
		//Accepting the cookies.
		WebElement cookie = driver.findElement(By.xpath("//a[@title='Accept Cookies']"));
		js.executeScript("arguments[0].click();", cookie);
		
		//Captha Handling for the 1st time
		Thread.sleep(45000);
		
		//submiting the registration details with empty message box
		WebElement ele = driver.findElement(By.xpath("//input[@id='Form_getForm_action_submitForm']"));
		js.executeScript("arguments[0].click();", ele);
		
		Thread.sleep(1000);
		
		//take first screenshot
		File shot1 = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		File tar1 = new File(System.getProperty("user.dir")+"\\ScreenShot\\shot01.png");
		Files.copy(shot1, tar1);
		
		
		//providing the message in your message text box
		driver.findElement(By.xpath("//textarea[@id='Form_getForm_Comment']")).sendKeys("My first Automation project...!");
		
		
		//Captha Handling for the 2nd time
		//Thread.sleep(30000);
		
		
		//submiting the registration details with all filled box.
		driver.findElement(By.xpath("//input[@id='Form_getForm_action_submitForm']")).click();
		
		
		//take second screenshot
		File shot2 = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		File tar2=new File(System.getProperty("user.dir")+"\\ScreenShot\\shot02.png");
		Files.copy(shot2, tar2);
		
		
		//Closing the current window( window2 ).
		driver.close();
		
		
		driver.switchTo().window(window1);
		
		
		//Validating the navigation.
		boolean nav = navigatingBack(window1);
		System.out.println("Is Navigating back to the previous Tab : "+nav);
		
		driver.quit();
		
		System.out.println("Automation Done");
		

	}

}
