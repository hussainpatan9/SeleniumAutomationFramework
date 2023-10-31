Feature: Manage Profile of Provider
  I want to use this template for my feature file

  @MangeProfile
  Scenario: Edit Manage Profile
    Given User is logged in to the Provider Portal
    And User naviates to Manage Profile
    When User edit provider profile
    And User Submits the changes
    Then the changes are reflected
