package com.evevoni.qa.testcases;

import com.evevoni.qa.base.TestBase;

import static com.evevoni.qa.base.TestBase.initialization;



import com.evevoni.qa.pages.HomePage;
import com.evevoni.qa.pages.MyAccountPage;
import com.evevoni.qa.util.TestUtil;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;



/**
 *
 * @author yvonneak
 */
public class MyAccountPageTest extends TestBase{
    TestUtil testUtil;
    HomePage homePage;
    MyAccountPage myAccountPage;
    
    String sheetName = "newUserRegisters";
    String sheetName2 = "userlogins";
    Logger log= Logger.getLogger(MyAccountPageTest.class);
    
    public MyAccountPageTest(){
        super();
    }
    
    @BeforeMethod
    public void setUp() throws InterruptedException{
        initialization();
        log.info("Loading browser");
        testUtil = new TestUtil();
        homePage = new HomePage();
        myAccountPage = new MyAccountPage();
        
        
        Thread.sleep(2000);
        
       homePage.clickCloseSubscribePopUp();
        Thread.sleep(1000);
        homePage.clickMyAccount();
        Thread.sleep(2000);
    }

    @DataProvider
    public Object[][] getLoginDataTest(){
        Object data[][]=TestUtil.getLonginData(sheetName2);
        return data;
    }
    
    @DataProvider
    public Object[][] getEvevoniTestData() throws IOException{
            Object data[][] = TestUtil.getTestData(sheetName);
            return data;
    }
    
   @Test(priority=1, dataProvider="getEvevoniTestData")
   public void accountRegistrationTest(String regEmail, String regPsswd, String billingFirstName, String billingLastNameField, String billingCountryField, String billingCity, String billingPostcode, String billingPhoneField) throws InterruptedException {
	   log.info("************ Performaning data Driven test ******************" );
       myAccountPage.newAccountReg(regEmail, regPsswd, billingFirstName, billingLastNameField, billingCountryField, billingCity, billingPostcode, billingPhoneField);
    }
   
   @Test(priority=2)
   public void accountLoginTest(){
       myAccountPage = myAccountPage.login(prop.getProperty("username"), prop.getProperty("password"));
       
   }

    @AfterMethod
    public void tearDown(){
      driver.quit();
      log.info("************Browser is closed ******************" );
    }

}

