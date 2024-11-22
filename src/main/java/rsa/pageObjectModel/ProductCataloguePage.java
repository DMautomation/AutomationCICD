package rsa.pageObjectModel;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rsa.AbstractComponents.AbstractComponents;

public class ProductCataloguePage extends AbstractComponents {
	
	public WebDriver driver;
	
	public ProductCataloguePage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css=".mb-3")
	private List<WebElement> products;
	
	By productBy = By.cssSelector(".mb-3");
	By addToCart = By.cssSelector(".card-body button:last-of-type");
	By spinner = By.cssSelector(".ng-animating");
	By toastMessage = By.cssSelector("#toast-container");
	By cart = By.cssSelector("[routerlink*='cart']");
	
	public List<WebElement> getProductList() throws InterruptedException
	{
		waitForElementToAppear(productBy);
		return products;
	}
	
	public WebElement getProductByName(String productName) throws InterruptedException
	{
		WebElement prod = getProductList().stream().filter(product->
		product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
		return prod;
	}
	
	public void addProductToCart(String productName) throws InterruptedException
	{
		WebElement prod =  getProductByName(productName);
		prod.findElement(addToCart).click();
		waitForElementToDisappear(spinner);
		//fluentWaitForElementToAppear(toastMessage);
		waitForElementToBeClickable(cart);
			
	}
	

}
