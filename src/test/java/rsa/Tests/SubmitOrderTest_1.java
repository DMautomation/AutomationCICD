//SubmitOrderTest_Original

package rsa.Tests;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import rsa.TestComponents.BaseTest;
import rsa.pageObjectModel.CartPage;
import rsa.pageObjectModel.CheckoutPage;
import rsa.pageObjectModel.OrderConfirmationPage;
import rsa.pageObjectModel.OrderPage;
import rsa.pageObjectModel.ProductCataloguePage;

public class SubmitOrderTest_1 extends BaseTest {
	String productName = "ZARA COAT 3";

	
	@SuppressWarnings("unused")
	@Test(dataProvider = "getData", groups = {"Purchase"})
	public void submitOrder(String email, String password, String productName) throws IOException, InterruptedException {

		ProductCataloguePage productCataloguePage = landingPage.loginApplication(email,
				password);

		// ProductCataloguePage productCataloguePage = new ProductCataloguePage(driver);
		List<WebElement> products = productCataloguePage.getProductList();
		productCataloguePage.addProductToCart(productName);

		CartPage cartPage = productCataloguePage.goToCartPage();
		// CartPage cartPage = new CartPage(driver);

		Boolean match = cartPage.verifyProductDisplay(productName);
		Assert.assertTrue(match, "Product Matched");
		CheckoutPage checkoutPage = cartPage.goToCheckout();

		// CheckoutPage checkoutPage = new CheckoutPage(driver);
		Boolean matchPM = checkoutPage.verifyPaymentMethod();
		Assert.assertTrue(matchPM, "Payment Method Matched");
		checkoutPage.verifyPaymentDetailsInputs();
		checkoutPage.selectCountry();
		OrderConfirmationPage orderConfirmationPage = checkoutPage.submitOrder();

		// OrderConfirmationPage orderConfirmationPage = new
		// OrderConfirmationPage(driver);
		Boolean msgMatch = orderConfirmationPage.verifyOrderMessage();
		Assert.assertTrue(msgMatch, "Order Place Message Matched");

	}

	@Test(dependsOnMethods = { "submitOrder" })
	public void OrderHistoryTest() {
		ProductCataloguePage productCataloguePage = landingPage.loginApplication("dmdebasish07@gmail.com",
				"Debasish@1234");
		OrderPage orderPage = productCataloguePage.goToOrdersPage();
		Assert.assertTrue(orderPage.verifyOrderDisplay(productName));
	}

	@DataProvider
	public Object[][] getData() {
		
		return new Object[][] { { "dmdebasish07@gmail.com", "Debasish@1234" ,"ZARA COAT 3"},
				{ "rnruchika05@gmail.com", "Ruchika@1234" ,"ADIDAS ORIGINAL"} };
	}

}
