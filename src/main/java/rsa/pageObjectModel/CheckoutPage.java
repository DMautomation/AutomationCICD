package rsa.pageObjectModel;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import rsa.AbstractComponents.AbstractComponents;

public class CheckoutPage extends AbstractComponents{

	WebDriver driver;

	public CheckoutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css = ".payment__type.payment__type--cc.active")
	private WebElement paymentMethod;
	
	@FindBy(css = "input[value='4542 9931 9292 2293']")
	private WebElement creditCardNumber;
	
	@FindBy(css = "select[class='input ddl']:first-of-type")
	private WebElement expMonthSelEle;
	
	@FindBy(css = "select[class='input ddl']:last-of-type")
	private WebElement expYearSelEle;
	
	@FindBy(xpath = "(//input[@type='text'])[2]")
	private WebElement cvvEle;
	
	@FindBy(xpath = "(//input[@type='text'])[3]")
	private WebElement nameOnCard;
	
	@FindBy(css = "input[placeholder='Select Country']")
	private WebElement country;
	
	@FindBy(css = ".ta-item:nth-of-type(2)")
	private WebElement selectedCountry;
	
	@FindBy(css = ".btnn.action__submit")
	private WebElement placeOrderBtn;
	
	
	public Boolean verifyPaymentMethod() {
		Boolean matchPM =  paymentMethod.getText().equalsIgnoreCase("Credit Card");
		return matchPM;
	}
	
	public void verifyPaymentDetailsInputs() {
		creditCardNumber.sendKeys("1111 2222 3333 4444");
		Select monthSel = new Select(expMonthSelEle);
		monthSel.selectByIndex(10);
		Select yearSel = new Select(expYearSelEle);
		yearSel.selectByIndex(24);
		cvvEle.sendKeys("123");
		nameOnCard.sendKeys("Mr. Debasish");
	}
	
	public void selectCountry() {
		Actions act = new Actions(driver);
		act.sendKeys(country, "india").build().perform();
		selectedCountry.click();
	}
	
	public OrderConfirmationPage submitOrder() {
		
		placeOrderBtn.click();
		OrderConfirmationPage orderCnfrmPage = new OrderConfirmationPage(driver);
		return orderCnfrmPage;	
	}
}

