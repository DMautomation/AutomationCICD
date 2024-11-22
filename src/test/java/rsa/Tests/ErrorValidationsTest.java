package rsa.Tests;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import rsa.TestComponents.BaseTest;
import rsa.TestComponents.Retry;
import rsa.pageObjectModel.CartPage;
import rsa.pageObjectModel.ProductCataloguePage;

public class ErrorValidationsTest extends BaseTest {
 
	
	@SuppressWarnings("unused")
	@Test(groups = {"ErrorHandling"},retryAnalyzer = Retry.class) //implementing retryAnalyzer for retry of flaky or probable failure test cases
	public void loginErrorValiadtion() throws IOException, InterruptedException {

		String productName = "ZARA COAT 3";
		ProductCataloguePage productCataloguePage = landingPage.loginApplication("dmdebasish07**@gmail.com",
				"Debasish@1234");
		
		Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());
	}
	
	@SuppressWarnings("unused")
	@Test
	public void ProductErrorValiadtion() throws IOException, InterruptedException {

		String productName = "ZARA COAT 3";
		ProductCataloguePage productCataloguePage = landingPage.loginApplication("rnruchika05@gmail.com",
				"Ruchika@1234");
		List<WebElement> products = productCataloguePage.getProductList();
		productCataloguePage.addProductToCart(productName);

		CartPage cartPage = productCataloguePage.goToCartPage();

		Boolean match = cartPage.verifyProductDisplay(productName);
		Assert.assertTrue(match, "Product Matched");
		
		
//		CheckoutPage checkoutPage = cartPage.goToCheckout();
//
//		Boolean matchPM = checkoutPage.verifyPaymentMethod();
//		Assert.assertTrue(matchPM, "Payment Method Matched");
//		checkoutPage.verifyPaymentDetailsInputs();
//		checkoutPage.selectCountry();
//		OrderConfirmationPage orderConfirmationPage = checkoutPage.submitOrder();
//
//		Boolean msgMatch = orderConfirmationPage.verifyOrderMessage();
//		Assert.assertTrue(msgMatch, "Order Place Message Matched");

	}
}
