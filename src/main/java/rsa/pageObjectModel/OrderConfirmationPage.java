package rsa.pageObjectModel;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rsa.AbstractComponents.AbstractComponents;

public class OrderConfirmationPage extends AbstractComponents{
	
	WebDriver driver;
	
	public OrderConfirmationPage(WebDriver driver) {
		
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css = ".hero-primary")
	private WebElement ordCnfrmMsg;
	
	public Boolean verifyOrderMessage() {
		System.out.println(ordCnfrmMsg.getText());
		Boolean msgMatch = ordCnfrmMsg.getText().equalsIgnoreCase("Thankyou for the order.");
		return msgMatch;
	}
	
	public String getConfirmationMessage() {
		String orderConfirmationMsg = ordCnfrmMsg.getText();
		return orderConfirmationMsg;
	}

}
