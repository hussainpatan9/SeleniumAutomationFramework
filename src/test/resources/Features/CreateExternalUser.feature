
Feature: Creating External User
  I want to use this template for my feature file

  @CreateExternalUser
  Scenario Outline: Create Provider User
    Given User is logged in to CRC Portal
    And navigates to Manage External User
    When User fills Create External User Form for Provider <email>
 		And User gets verification link from <email>
 		

   Examples: 
      | email                            | password      |
        | user2@mailinator.com             | Pass456!      |
  