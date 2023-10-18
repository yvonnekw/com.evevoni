package com.evevoni.qa.pages;

import com.evevoni.qa.base.TestBase;
import static com.evevoni.qa.base.TestBase.driver;
import org.openqa.selenium.By;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;


/**
 *
 * @author yvonneak
 */
public class MyAccountPage extends TestBase{
    Actions actions; 
    //for logging in
    @FindBy(xpath="//input[@id='username']")     
    WebElement acctUsername;
    @FindBy(id="password")
    WebElement acctPassword;
    @FindBy(name="login")
    WebElement acctLoginBtn;
    @FindBy(id="rememberme")
    WebElement acctRemembermeCheckbox;
    @FindBy(xpath="//a[contains(text(),'Lost your password?']")
    WebElement acctLostYourPasswordLink;
    
    
    //For registering user
    @FindBy(id="reg_email")
    WebElement acctReg_email;
    @FindBy(id="reg_password")
    WebElement acctReg_password;
    @FindBy(name="register")
    WebElement acctRegisterBtn;
    @FindBy(xpath="a[contains(text(),'Evevoni')]")
    WebElement evevoniLogo;
    @FindBy(xpath="//a[text()='Addresses']")
    WebElement myAccountAddressesLink;
    
    //for billingAddress
    //edit link
    @FindBy(xpath="//div[@class='u-column1 col-1 woocommerce-Address']/header[@class='woocommerce-Address-title title']/a[text()='Edit']")
    WebElement myAccountAddressesBillingEditLink;
    @FindBy(id="ship-to-different-address-checkbox")
    WebElement shipToDifferentAddressCheckbox;
    @FindBy(id="billing_first_name")
    WebElement myAccountAddressesBillingFirstName;
    @FindBy(id="billing_last_name")
    WebElement myAccountAddressesBillingLastNameField;
    @FindBy(id="billing_company")
    WebElement myAccountAddressesBillingCompany;
    @FindBy(id="billing_country")
    WebElement myAccountAddressesBillingCountryField;
    @FindBy(id="billing_address_1")
    WebElement myAccountAddressesBillingAddress1;
    @FindBy(id="billing_city")
    WebElement myAccountAddressesBillingCity;
    @FindBy(id="billing_postcode")
    WebElement myAccountAddressesBillingPostcode;
    @FindBy(id="billing_phone_field")
    WebElement myAccountAddressesBillingPhoneField;
     @FindBy(name="save_address")
    WebElement myAccountAddressesSaveBtn;
   
    public static MyAccountPage myAccountPage;
    
    //inintialise pageFactory
    public MyAccountPage(){
        
        PageFactory.initElements(driver, this);
    }
     public String validateMyAccountPageTitle(){
        return driver.getTitle();
    }
     public boolean validateEvevoniLogo(){
         return evevoniLogo.isDisplayed();
     }
     public MyAccountPage login(String usname, String passwd){
         acctUsername.sendKeys(usname);
         acctPassword.sendKeys(passwd);
         
                JavascriptExecutor js = (JavascriptExecutor)driver;
                js.executeScript("arguments[0].click();", acctLoginBtn);
         
         return new MyAccountPage();
     }
     //click on the address link in the accountPage to complete reg
     public MyAccountPage newAccountReg(String regEmail, String regPsswd, String billingFirstName, String billingLastNameField, String billingCountryField, String billingCity, String billingPostcode, String billingPhoneField) throws InterruptedException{
         actions =new Actions(driver);
         myAccountPage= new MyAccountPage();
         acctReg_email.sendKeys(regEmail);
         acctReg_password.sendKeys(regPsswd);
         acctRegisterBtn.click();
         
         Thread.sleep(2000);
         String registrationErrorMeg =driver.findElement(By.xpath("//ul[@class='woocommerce-error']")).getText();
         
         if(registrationErrorMeg.contains("Error: An account is already registered with your email address. Please log in.")){
             System.out.println("Registration error message " + registrationErrorMeg);
            //call the login method
             login(regEmail,regPsswd);
         }
         else{
         
         //click on Addresses link and enter details
         Thread.sleep(2000);
         myAccountAddressesLink.click();
         Thread.sleep(2000);
         myAccountAddressesBillingEditLink.click();
         Thread.sleep(2000);
         //scroll down to fucus on element
         actions.moveToElement(myAccountAddressesBillingFirstName);
         actions.click();
         actions.sendKeys(billingFirstName);
         actions.build().perform();
         
         actions.moveToElement(myAccountAddressesBillingLastNameField);
         actions.click();
         actions.sendKeys(billingLastNameField);
         actions.build().perform();
         Select countryField;
         countryField = new Select(driver.findElement(By.id("billing_country")));
         countryField.selectByValue(billingCountryField);
         actions.moveToElement(myAccountAddressesBillingCity);
         actions.click();
         actions.sendKeys(billingCity);
         actions.build().perform();
         actions.moveToElement(myAccountAddressesBillingPostcode);
         actions.click();
         actions.sendKeys(billingPostcode);
         actions.build().perform();
         actions.moveToElement(myAccountAddressesBillingPhoneField);
         actions.click();
         actions.sendKeys(billingPhoneField);
         actions.build().perform();
         actions.click();
         myAccountAddressesSaveBtn.click();
         actions.build().perform();
         Thread.sleep(2000);
         String messageAddressEntry =driver.findElement(By.xpath("//div[@class='woocommerce-message']")).getText();
         
         if (messageAddressEntry.contains("Address changed successfully.")){
             System.out.println("Successfully entered address details " + messageAddressEntry);
         }
         else{
             System.out.println("UnSuccessful address details entered " + messageAddressEntry);
         }

         }//end of else
         Thread.sleep(1000);
         return new MyAccountPage();
                  
     }

     //scroll down page to focus on elements
     public void scrollDownPage(){
         JavascriptExecutor js = (JavascriptExecutor)driver;
         js.executeScript("window.scrollBy(0,800)");
     }

}

