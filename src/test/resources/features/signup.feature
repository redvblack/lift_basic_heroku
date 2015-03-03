Feature: In order to have authorisation
         I want to be able to use Lift Users
         So that I can Signup, login and log out

  Scenario: 
    Given I have browsed to the home page
    Then I should see the welcome text

  Scenario: User signup page is available
    Given I have browsed to the home page
    When I click the "Sign Up" link
    Then I should see the Sign Up page
    And I should see the sign up text

  Scenario: User login page is available
    Given I have browsed to the home page
    When I click the "Login" link
    Then I should see the Login page
    And I should see the login text

  