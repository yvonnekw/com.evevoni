package com.evevoni.qa.testcases;

import com.evevoni.qa.base.TestBase;
import static com.evevoni.qa.base.TestBase.driver;
import com.evevoni.qa.pages.CheckOutPage;
import com.evevoni.qa.pages.HomePage;
import com.evevoni.qa.pages.MyAccountPage;
import com.evevoni.qa.pages.SubscribeToOurNewsLettersPage;
import com.evevoni.qa.pages.WigCapOffersPage;
import com.evevoni.qa.util.TestUtil;

import org.apache.log4j.Logger;
import org.eclipse.jetty.util.log.Log;
import org.junit.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *test cases should be separated -- independent with each other
//before each test case -- launch the browser and login
//@test -- execute test case
//after each test case -- close the browser
 * @author yvonneak
 */
public class HomePageTest extends TestBase{
    
    CheckOutPage checkOutPage;
    HomePage homePage;
    MyAccountPage myAccountPage;
    SubscribeToOurNewsLettersPage  subscribeToOurNewsLettersPage;
    WigCapOffersPage wigCapOffersPage;
    TestUtil testUtil;
    Logger log= Logger.getLogger(MyAccountPageTest.class);
    
    public HomePageTest(){
        super();
    }

    @BeforeMethod
    public void setUp() throws InterruptedException{
        initialization();
        testUtil = new TestUtil();
        checkOutPage = new CheckOutPage();
        homePage = new HomePage();
        myAccountPage = new MyAccountPage();
        Thread.sleep(2000);
        homePage.clickCloseSubscribePopUp();

        Thread.sleep(2000);
        homePage.clickMyAccount();
        Thread.sleep(2000);
        String accPTitle = myAccountPage.validateMyAccountPageTitle();
        System.out.println(accPTitle);
        
        Assert.assertEquals(accPTitle, "My account – Evevoni");
        //logging in would retrun us to the account page
       myAccountPage= myAccountPage.login(prop.getProperty("username"), prop.getProperty("password"));
    }

     @Test(priority=1)
     public void verifyCorrectUserNameTest(){
         Assert.assertTrue(homePage.verifyCorrectUserName());
     }

     @AfterMethod
    public void tearDown(){
       
      driver.quit();
    }
}



