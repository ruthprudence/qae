Feature: Login Functionality
  As a user of the website
  I want to be able to log in with my credentials
  So that I can access my account

  Background:
    Given I am on the login page

  Scenario: Successful login with valid credentials
    When I enter username "validuser"
    And I enter password "validpass"
    And I click the login button
    Then I should be redirected to the home page
    And I should see a welcome message

  Scenario Outline: Failed login with invalid credentials
    When I enter username "<username>"
    And I enter password "<password>"
    And I click the login button
    Then I should see an error message "<errorMessage>"

    Examples:
      | username    | password    | errorMessage                  |
      | invaliduser | validpass   | Invalid username or password. |
      | validuser   | invalidpass | Invalid username or password. |
      |             | validpass   | Username is required.         |
      | validuser   |             | Password is required.         |