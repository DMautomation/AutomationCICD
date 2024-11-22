//SubmitOrderTest2_ReadData_Using_JSON

package rsa.Tests;

import java.io.IOException;
import java.util.HashMap;
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

public class SubmitOrderTest extends BaseTest {
	String productName = "ZARA COAT 3";

	@SuppressWarnings("unused")
	@Test(dataProvider = "getData", groups = { "Purchase" })
	public void submitOrder(HashMap<String, String> input) throws IOException, InterruptedException {

		ProductCataloguePage productCataloguePage = landingPage.loginApplication(input.get("email"),
				input.get("password"));

		List<WebElement> products = productCataloguePage.getProductList();
		productCataloguePage.addProductToCart(input.get("product"));

		CartPage cartPage = productCataloguePage.goToCartPage();

		Boolean match = cartPage.verifyProductDisplay(input.get("product"));
		Assert.assertTrue(match, "Product Matched");
		CheckoutPage checkoutPage = cartPage.goToCheckout();

		Boolean matchPM = checkoutPage.verifyPaymentMethod();
		Assert.assertTrue(matchPM, "Payment Method Matched");
		checkoutPage.verifyPaymentDetailsInputs();
		checkoutPage.selectCountry();
		OrderConfirmationPage orderConfirmationPage = checkoutPage.submitOrder();
		
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
	public Object[][] getData() throws IOException {

		List<HashMap<String, String>> data = getJasonDataToMap(System.getProperty("user.dir") +"\\src\\test\\java\\rsa\\Data\\PurchaseOrder.json");
		return new Object[][] { { data.get(0) }, { data.get(1) } };
	}
	

	

	
	
	
	
//	@DataProvider
//	public Object[][] getData() {
//		
//		return new Object[][] { { "dmdebasish07@gmail.com", "Debasish@1234" ,"ZARA COAT 3"},
//				{ "rnruchika05@gmail.com", "Ruchika@1234" ,"ADIDAS ORIGINAL"} };
//	}
	
	
//	@DataProvider
//	public Object[][] getData() {
//
//		HashMap<Object, Object> map = new HashMap<Object, Object>();
//		map.put("email", "dmdebasish07@gmail.com");
//		map.put("passworrd", "Debasish@1234");
//		map.put("product", "ZARA COAT 3");
//
//		HashMap<Object, Object> map1 = new HashMap<Object, Object>();
//		map1.put("email", "rnruchika05@gmail.com");
//		map1.put("passworrd", "Ruchika@1234");
//		map1.put("product", "ADIDAS ORIGINAL");
//
//		return new Object[][] { { map }, { map1 } };
//	}

}
