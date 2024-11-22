package rsa.Tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

//import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAloneTest {

	public static void main(String[] args) throws InterruptedException {
		
		String productName = "ZARA COAT 3";
		
		//System.setProperty("webdriver.chrome.driver","H:\\browser-drivers\\chromedriver.exe");
		//WebDriverManager.chromedriver().arch64().setup();
		WebDriver driver = new ChromeDriver();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		
		driver.get("https://rahulshettyacademy.com/client");
		
		driver.findElement(By.id("userEmail")).sendKeys("dmdebasish07@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Debasish@1234");
		driver.findElement(By.id("login")).click();
		
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
	
		WebElement prod = products.stream().filter(product->
		product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
		
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		Wait<WebDriver> fwait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(20))  // Maximum time to wait
                .pollingEvery(Duration.ofSeconds(2))  // Frequency to check the condition
                .ignoring(NoSuchElementException.class);  // Ignore this exception during the wait

		
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating"))); // wait for loading icon element to disappear
		fwait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));// wait for product added successfully message to be displayed
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[routerlink*='cart']")));	
		
		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
		List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
		Boolean match = cartProducts.stream().anyMatch(cartProduct->cartProduct.getText().equalsIgnoreCase(productName));
		
		Assert.assertTrue(match, "Product Matched");
		
		driver.findElement(By.cssSelector(".totalRow button")).click();
		
		WebElement creditCardInput = driver.findElement(By.cssSelector("input[value='4542 9931 9292 2293']"));
		
		creditCardInput.clear();
		creditCardInput.sendKeys("1111 2222 3333 4444");
		
		WebElement expMonthSelEle = driver.findElement(By.cssSelector("select[class='input ddl']:first-of-type"));
		Select expMonthSel = new Select(expMonthSelEle);
		expMonthSel.selectByIndex(10);
		
		WebElement expYearSelEle = driver.findElement(By.cssSelector("select[class='input ddl']:last-of-type"));
		Select expYearSel = new Select(expYearSelEle);
		expYearSel.selectByIndex(24);
		
		driver.findElement(By.xpath("(//input[@type='text'])[2]")).sendKeys("124");
		driver.findElement(By.xpath("(//input[@type='text'])[3]")).sendKeys("Mr. Debasish");
	
//		driver.findElement(By.xpath("(//input[@type='text'])[4]")).sendKeys("rahulshettyacademy");		
//		driver.findElement(By.cssSelector("button[type='submit']")).click();
		
		fwait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[placeholder='Select Country']")));
		
		WebElement country= driver.findElement(By.cssSelector("input[placeholder='Select Country']"));
		
		Actions act = new Actions(driver);
		act.sendKeys(country, "india").build().perform();
		
		fwait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results.list-group.ng-star-inserted")));
		
		driver.findElement(By.cssSelector(".ta-item:nth-of-type(2)")).click();
		
		driver.findElement(By.cssSelector(".btnn.action__submit")).click();

		
		String ordCnfrmMsg = driver.findElement(By.cssSelector(".hero-primary")).getText();
		System.out.println(ordCnfrmMsg);
		Assert.assertTrue(ordCnfrmMsg.equalsIgnoreCase("Thankyou for the order."));	
		
	}
}
