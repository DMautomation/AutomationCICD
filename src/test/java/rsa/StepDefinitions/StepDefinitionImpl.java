package rsa.StepDefinitions;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import rsa.TestComponents.BaseTest;
import rsa.pageObjectModel.CartPage;
import rsa.pageObjectModel.CheckoutPage;
import rsa.pageObjectModel.LandingPage;
import rsa.pageObjectModel.OrderConfirmationPage;
import rsa.pageObjectModel.ProductCataloguePage;

public class StepDefinitionImpl extends BaseTest 
{

	public LandingPage landingPage;
	public ProductCataloguePage productCataloguePage;
	public OrderConfirmationPage orderConfirmationPage;
	
	@Given("I landed on Ecommerce Page")
	public void I_landed_on_Ecommerce_Page() throws IOException 
	{
		landingPage = launchApplication();
	}
	
	@Given("^Logged in with username (.+) and password (.+)$")
	public void Logged_in_with_username_and_password(String username, String password)
	{
		productCataloguePage = landingPage.loginApplication(username, password);
	}
	
	@SuppressWarnings("unused")
	@When("^I add product (.+) to Cart$")
	public void I_add_product_to_Cart(String productName) throws InterruptedException 
	{
		List<WebElement> products = productCataloguePage.getProductList();
		productCataloguePage.addProductToCart(productName);	
	}
	
	@When("^Checkout (.+) and submit the order$")
	public void Checkout_and_submit_the_order(String productName)
	{
		CartPage cartPage = productCataloguePage.goToCartPage();
		
		Boolean match = cartPage.verifyProductDisplay(productName);
		Assert.assertTrue(match, "Product Matched");
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.verifyPaymentDetailsInputs();
		checkoutPage.selectCountry();
		orderConfirmationPage = checkoutPage.submitOrder();
	}
	
	@Then("{string} message is displayed on ConfirmationPage")
	public void message_displayed_on_ConfirmationPage(String string) 
	{
		String confirmMassege = orderConfirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMassege.equalsIgnoreCase(string));
		driver.close();
	}
	
	@Then("{string} message is displayed")
	public void alert_message_displayed(String string) 
	{
		Assert.assertEquals(string, landingPage.getErrorMessage());
		driver.close();
	}
	
	
	
}
