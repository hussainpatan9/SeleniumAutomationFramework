

Feature: Title of your feature
  I want to use this template for my feature file

 	@Login
  Scenario Outline: Login to Insted Provider
    Given User is on Provider Login Screen
    When User Enter <email> and <password>
    And User click on Submit
    Then User is logged in

      Examples:
        | email                            | password      |
        | user1@example.com                | Pass123!      |
        | user2@mailinator.com             | Pass456!      |
  