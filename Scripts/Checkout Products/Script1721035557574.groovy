import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

WebUI.callTestCase(findTestCase('TC - Login'), [('username') : 'standard_user', ('password') : 'secret_sauce'
        , ('expectedText') : 'Products'], FailureHandling.STOP_ON_FAILURE)

xpath = "(//button[.='ADD TO CART'])"

TestObject object = new TestObject().addProperty('xpath', ConditionType.EQUALS, xpath)

List<TestObject> element = WebUI.findWebElements(object, 30)

int count = element.size()

for (int i = count; i >= 1; i--) {
	new_xpath = xpath + "[$i]"
	new_object = new TestObject().addProperty("xpath", ConditionType.EQUALS, new_xpath)
	WebUI.click(new_object)
}

countCart = WebUI.getText(new TestObject().addProperty("xpath", ConditionType.EQUALS, "//span[@class='fa-layers-counter shopping_cart_badge']"))
assert countCart.toInteger() == count

WebUI.scrollToElement(findTestObject('Object Repository/btn_shoppingCart'), 0)
WebUI.click(findTestObject('Object Repository/btn_shoppingCart'))

int totalPrice = 0
for (int i = 1; i <= count; i++) {
	itemPrice = new TestObject().addProperty('xpath', ConditionType.EQUALS, "(//div[@class='inventory_item_price'])[$i]")
	println(itemPrice)

	priceText = WebUI.getText(itemPrice)
	int price = priceText.replaceAll('[^0-9]', '').toInteger()
	
	totalPrice += price
	
}
println(totalPrice)

WebUI.click(findTestObject('Object Repository/btn_Checkout'))

WebUI.setText(findTestObject('Object Repository/input', [('id') : 'first-name']), 'Fetty')
WebUI.setText(findTestObject('Object Repository/input', [('id') : 'last-name']), 'Maula')
WebUI.setText(findTestObject('Object Repository/input', [('id') : 'postal-code']), '12345')

WebUI.click(findTestObject('Object Repository/btn_Continue'))

subtotalText = WebUI.getText(new TestObject().addProperty("class", ConditionType.EQUALS, "summary_subtotal_label"))
subtotal = subtotalText.replaceAll('[^0-9]', '').toInteger()

assert subtotal == totalPrice