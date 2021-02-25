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
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

CustomKeywords.'genericMethods.generic_methods.navigate_to_url'(GlobalVariable.G_url);

CustomKeywords.'helper_methods.helper_methods.loginToApplication'();

WebUI.waitForElementVisible(findTestObject('Login_UserInfoEdit_Logout/Page_BenefitHub/display_name', ['display_name' : 'Aspire QATEST']), 60)

//WebUI.scrollToElement(findTestObject('Deal_Edits/Page_BenefitHub/a_Menu'), 30)

//WebUI.mouseOver(findTestObject('Deal_Edits/Page_BenefitHub/a_Menu', ['menu':'Pets']))

WebUI.mouseOver(findTestObject('Deal_Edits/Page_BenefitHub/a_Menu'))
	
WebUI.click(findTestObject('Deal_Edits/Page_BenefitHub/a_Pet Accessories', ['submenu' : 'Pet Accessories']))

WebUI.click(findTestObject('Deal_Edits/Page_BenefitHub/a_Edit'))

CustomKeywords.'genericMethods.generic_methods.scroll_to_element'(findTestObject('Deal_Edits/Page_BenefitHub/h1_Pets_Pet_Accessories'))

//WebUI.click(findTestObject('Deal_Edits/Page_BenefitHub/h1_Pets_Pet_Accessories'))

WebUI.click(findTestObject('Deal_Edits/Page_BenefitHub/p_Active offer-11'))

WebUI.click(findTestObject('Deal_Edits/Page_BenefitHub/button_Get Deal'))

CustomKeywords.'genericMethods.generic_methods.switch_to_window_by_title'('Pet Supplies, Accessories and Products Online | PetSmart')
WebUI.click(findTestObject('Deal_Edits/Page_Pet Supplies, Accessories and Products_cdc6e2/img'))

CustomKeywords.'genericMethods.generic_methods.switch_to_window_by_title'('BenefitHub')
WebUI.click(findTestObject('Deal_Edits/Page_BenefitHub/button_Feedback'))

WebUI.click(findTestObject('Deal_Edits/Page_BenefitHub/i_Love this offer_far fa-heart'))

WebUI.click(findTestObject('Deal_Edits/Page_BenefitHub/h4_Alert'))

WebUI.click(findTestObject('Deal_Edits/Page_BenefitHub/x_small_modal'))

WebUI.click(findTestObject('Deal_Edits/Page_BenefitHub/x_large_modal'))

WebUI.click(findTestObject('Deal_Edits/Page_BenefitHub/div_Pet Stores  Food_edit_btns'))

WebUI.click(findTestObject('Deal_Edits/Page_BenefitHub/i_Pet Stores  Food_far fa-edit'))

WebUI.click(findTestObject('Deal_Edits/Page_BenefitHub/span_Change Image'))

WebUI.click(findTestObject('Deal_Edits/Page_BenefitHub/h2_Upload Images and Files'))

WebUI.click(findTestObject('Deal_Edits/Page_BenefitHub/div_Files'))

WebUI.click(findTestObject('Deal_Edits/Page_BenefitHub/div_Images'))

WebUI.click(findTestObject('Deal_Edits/Page_BenefitHub/span_IndiaFlag.png'))

WebUI.clearText(findTestObject('Deal_Edits/Page_BenefitHub/input_Change Image_OfferTitle'))

WebUI.setText(findTestObject('Deal_Edits/Page_BenefitHub/input_Change Image_OfferTitle'), 
    'Active offer-111')

WebUI.clearText(findTestObject('Deal_Edits/Page_BenefitHub/input_Required_TypeDetails'))
WebUI.setText(findTestObject('Deal_Edits/Page_BenefitHub/input_Required_TypeDetails'), 
    '10')

WebUI.click(findTestObject('Deal_Edits/Page_BenefitHub/div_Test offer'))

//WebUI.clearText(findTestObject('Deal_Edits/Page_BenefitHub/div_Test offers'))
WebUI.setText(findTestObject('Deal_Edits/Page_BenefitHub/div_Test offers'), 
    '<div data-contents="true"><div data-block="true" data-editor="88jv3" data-offset-key="5vm9c-0-0"><div data-offset-key="5vm9c-0-0" class="public-DraftStyleDefault-block public-DraftStyleDefault-ltr" style=""><span data-offset-key="5vm9c-0-0"><span data-text="true">Test offers</span></span></div></div></div>')

WebUI.click(findTestObject('Deal_Edits/Page_BenefitHub/input_Offer Link Text_LinkURL'))

WebUI.click(findTestObject('Deal_Edits/Page_BenefitHub/input_Required_IsActive'))

WebUI.click(findTestObject('Deal_Edits/Page_BenefitHub/input_Required_IsActive'))

WebUI.click(findTestObject('Deal_Edits/Page_BenefitHub/input_Required_FreeShipping'))

WebUI.clearText(findTestObject('Deal_Edits/Page_BenefitHub/input_Search Tags (Keywords for search)_Sta_00df9e'))

WebUI.setText(findTestObject('Deal_Edits/Page_BenefitHub/input_Search Tags (Keywords for search)_Sta_00df9e'), 
    '02/03/2021')

WebUI.clearText(findTestObject('Deal_Edits/Page_BenefitHub/input_Offer Quality Score_EndDate_react_dp _6cabd3'))

WebUI.setText(findTestObject('Deal_Edits/Page_BenefitHub/input_Offer Quality Score_EndDate_react_dp _6cabd3'), 
    '09/03/2021')

WebUI.click(findTestObject('Deal_Edits/Page_BenefitHub/button_Save'))

WebUI.click(findTestObject('Deal_Edits/Page_BenefitHub/p_Active offer-111'))

WebUI.click(findTestObject('Deal_Edits/Page_BenefitHub/span_10 Gift Card'))

WebUI.click(findTestObject('Deal_Edits/Page_BenefitHub/h3_Active offer-111'))

WebUI.click(findTestObject('Deal_Edits/Page_BenefitHub/x_large_modal'))

CustomKeywords.'genericMethods.generic_methods.scrollToElement'(findTestObject('Deal_Edits/Page_BenefitHub/a_Save'))

WebUI.click(findTestObject('Deal_Edits/Page_BenefitHub/a_Save'))

WebUI.click(findTestObject('Deal_Edits/Page_BenefitHub/h3_Top Brands'))
/*
 * WebUI.click(findTestObject('Deal_Edits/Page_BenefitHub/div_Shop By BrandsSort
 * by FeaturedFeaturedN_96be9c'))
 * 
 * WebUI.click(findTestObject('Deal_Edits/Page_BenefitHub/div_5 Gift Card1
 * DealShop Brand'))
 * 
 * WebUI.click(findTestObject('Deal_Edits/Page_BenefitHub/a_Shop Brand'))
 * 
 * WebUI.click(findTestObject('Deal_Edits/Page_BenefitHub/button_View Deal'))
 * 
 * WebUI.click(findTestObject('Deal_Edits/Page_BenefitHub/x_large_modal'))
 * 
 * WebUI.click(findTestObject('Deal_Edits/Page_BenefitHub/h3_Top Deal'))
 * 
 * WebUI.click(findTestObject('Deal_Edits/Page_BenefitHub/div_Up to 20 offLocal
 * offerView Top Deal'))
 * 
 * WebUI.click(findTestObject('Deal_Edits/Page_BenefitHub/button_View Top
 * Deal'))
 * 
 * WebUI.click(findTestObject('Deal_Edits/Page_BenefitHub/button_Get Deal'))
 * 
 * WebUI.click(findTestObject('Deal_Edits/Page_BenefitHub/x_large_modal'))
 * 
 * WebUI.click(findTestObject('Deal_Edits/Page_BenefitHub/i_Aspire QATEST_fas
 * icon-right fa-caret-down'))
 * 
 * WebUI.click(findTestObject('Deal_Edits/Page_BenefitHub/a_Logout'))
 */

WebUI.closeBrowser()

