Feature: Customers

  Background: Below are common steps for each scenario
    Given User Launch Chrome browser
    When User opens URL "http://admin-demo.nopcommerce.com/login"
    And User enters Email as "admin@yourstore.com" and Password as "admin"
    And Click on Login
    Then User can view Dashboard

  @Sanity
  Scenario: Add a new Customer
    When User clicks on customers Menu
    And Click on customers Menu Item
    And Click on Add new button
    Then User can View Add new customer page
    When User enters customer info
    And Click on Save button
    Then User can view confirmation message "The new customer has been added successfully."
    And close browser

  @Regression
  Scenario: Search Customer by EMailID
    When User clicks on customers Menu
    And Click on customers Menu Item
    And Enter customer EMail
    When Click on search button
    Then User should found Email in the Search table
    And close browser

  @Regression
  Scenario: Search Customer by Name
    When User clicks on customers Menu
    And Click on customers Menu Item
    And Enter customer FirstName
    And Enter customer LastName
    When Click on search button
    Then User should found Name in the Search table
    And close browser