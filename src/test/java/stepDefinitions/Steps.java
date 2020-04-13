package stepDefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import pageObjects.AddCustomerPage;
import pageObjects.LoginPage;
import pageObjects.SearchCustomerPage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Steps extends BaseClass {

    @Before
    public void setup() throws IOException {
        //Logging
        logger=Logger.getLogger("nopCommerceSDET");
        PropertyConfigurator.configure("Log4j.properties");
        logger.setLevel(Level.DEBUG);

        //Load properties file
        configProp= new Properties();
        FileInputStream configPropfile = new FileInputStream("config.properties");
        configProp.load(configPropfile);

        String br=configProp.getProperty("browser"); //getting the browser name from config.properties file

        //Launching browser
        if (br.equals("firefox")) {
            System.setProperty("webdriver.gecko.driver",configProp.getProperty("firefoxpath"));
            driver = new FirefoxDriver();
        }

        else if (br.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver",configProp.getProperty("chromepath"));
            driver = new ChromeDriver();
        }

        else if (br.equals("ie")) {
            System.setProperty("webdriver.ie.driver",configProp.getProperty("iepath"));
            driver = new InternetExplorerDriver();
        }
    }

    @Given("User Launch Chrome browser")
    public void user_Launch_Chrome_browser() {
        logger.info("************* Launching Browser *****************");
        loginPage = new LoginPage(driver);
    }

    @When("User opens URL {string}")
    public void user_opens_URL(String url) {
        logger.info("********** Opening URL*********");
        driver.get(url);
        driver.manage().window().maximize();
    }

    @When("User enters Email as {string} and Password as {string}")
    public void user_enters_Email_as_and_Password_as(String email, String password) {
        logger.info("********** Providing login details*********");
        loginPage.setUserName(email);
        loginPage.setPassword(password);

    }

    @When("Click on Login")
    public void click_on_Login() {
        logger.info("********** Started login*********");
        loginPage.clickLogin();
    }

    @Then("Page title should be {string}")
    public void page_title_should_be(String title) throws InterruptedException {
        if (driver.getPageSource().contains("Login was unsuccessful.")) {
            driver.close();
            logger.info("********** Loging passed*********");
            Assert.assertTrue(false);
        } else {
            logger.info("********** Login failed*********");
            Assert.assertEquals(title, driver.getTitle());
        }
        Thread.sleep(3000);

    }

    @When("User clicks on Log out link")
    public void user_clicks_on_Log_out_link() {
        logger.info("********** Click on Logout link*********");
        loginPage.clickLogout();
    }

    @Then("close browser")
    public void close_browser() {
        logger.info("********** Closing browser*********");
        driver.quit();
    }

    //Customers feature steps definitions...

    @Then("User can view Dashboard")
    public void user_can_view_Dashboard() {
        addCustomerPage = new AddCustomerPage(driver);
        logger.info("********* Verifying Dashboard page title after login successful **************");
        Assert.assertEquals("Dashboard / nopCommerce administration", addCustomerPage.getPageTitle());
    }

    @When("User clicks on customers Menu")
    public void user_clicks_on_customers_Menu() throws InterruptedException {
        Thread.sleep(3000);
        addCustomerPage.clickOnCustomersMenu();
    }

    @When("Click on customers Menu Item")
    public void click_on_customers_Menu_Item() throws InterruptedException {
        Thread.sleep(2000);
        addCustomerPage.clickOnCustomersMenuItem();
    }

    @When("Click on Add new button")
    public void click_on_Add_new_button() throws InterruptedException {
        addCustomerPage.clickOnAddnew();
        Thread.sleep(2000);
    }

    @Then("User can View Add new customer page")
    public void user_can_View_Add_new_customer_page() {
        Assert.assertEquals("Add a new customer / nopCommerce administration", addCustomerPage.getPageTitle());
    }

    @When("User enters customer info")
    public void user_enters_customer_info() throws InterruptedException {
        logger.info("********** Adding new customer*********");
        logger.info("********** Proving customer details*********");
        String email = randomestring() + "@gmail.com";
        addCustomerPage.setEmail(email);
        addCustomerPage.setPassword("test123");
        // Registered - default
        // The customer cannot be in both 'Guests' and 'Registered' customer roles
        // Add the customer to 'Guests' or 'Registered' customer role
        addCustomerPage.setCustomerRoles("Guest");
        Thread.sleep(3000);

        addCustomerPage.setManagerOfVendor("Vendor 2");
        addCustomerPage.setGender("Male");
        addCustomerPage.setFirstName("Pavan");
        addCustomerPage.setLastName("Kumar");
        addCustomerPage.setDob("7/05/1985"); // Format: D/MM/YYY
        addCustomerPage.setCompanyName("busyQA");
        addCustomerPage.setAdminContent("This is for testing.........");
    }

    @When("Click on Save button")
    public void click_on_Save_button() throws InterruptedException {
        logger.info("********** Saving customer data*********");
        addCustomerPage.clickOnSave();
        Thread.sleep(2000);
    }

    @Then("User can view confirmation message {string}")
    public void user_can_view_confirmation_message(String msg) {
        Assert.assertTrue(driver.findElement(By.tagName("body")).getText()
                .contains(msg));
    }

    //Searching customer by email ID.............................
    @When("Enter customer EMail")
    public void enter_customer_EMail() {
        searchCust = new SearchCustomerPage(driver);
        logger.info("********* Searching customer details by Email **************");
        searchCust.setEmail("victoria_victoria@nopCommerce.com");
    }

    @When("Click on search button")
    public void click_on_search_button() throws InterruptedException {
        searchCust.clickSearch();
        Thread.sleep(3000);
    }

    @Then("User should found Email in the Search table")
    public void user_should_found_Email_in_the_Search_table() {
        boolean status = searchCust.searchCustomerByEmail("victoria_victoria@nopCommerce.com");
        Assert.assertEquals(true, status);
    }

    //steps for searching a customer by Name................
    @When("Enter customer FirstName")
    public void enter_customer_FirstName() {
        logger.info("********* Searching customer details by Name **************");
        searchCust = new SearchCustomerPage(driver);
        searchCust.setFirstName("Victoria");
    }

    @When("Enter customer LastName")
    public void enter_customer_LastName() {
        searchCust.setLastName("Terces");
    }

    @Then("User should found Name in the Search table")
    public void user_should_found_Name_in_the_Search_table() {
        boolean status = searchCust.searchCustomerByName("Victoria Terces");
        Assert.assertEquals(true, status);
    }
}
