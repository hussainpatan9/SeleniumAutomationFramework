
# Selenium Automation Project

This project is a Maven-based Selenium Automation Framework for testing my personal project. It includes automated tests for various functionalities using Cucumber and Java.

## Table of Contents

- [Selenium Automation Project](#selenium-automation-project)
  - [Table of Contents](#table-of-contents)
  - [Introduction](#introduction)
  - [Prerequisites](#prerequisites)
  - [Project Structure](#project-structure)
  - [Configuration](#configuration)
  - [Helper Class](#helper-class)
  - [How to Run](#how-to-run)
  - [Test Reports](#test-reports)
  - [Contributing](#contributing)

## Introduction

This Selenium Automation Project is designed to automate testing for my personal project. It utilizes the Cucumber framework for behavior-driven development (BDD) and Java for test automation.

## Prerequisites

Make sure you have the following installed on your machine:

- Java
- Maven
- Your preferred IDE (IntelliJ, Eclipse, etc.)
- WebDriver (Chrome, Firefox, etc.)

## Project Structure

The project follows a standard Maven project structure:

```
├── src
│   ├── main
│   │   └── java
│   │       └── ...
│   └── test
│       └── java
│           └── ...
├── resources
│   ├── config
│   │   └── ...
│   ├── features
│   │   └── ...
│   └── ...
├── target
├── .gitignore
├── pom.xml
└── README.md
```

- `src/main/java`: Contains the main source code for the project.
- `src/test/java`: Houses the test code, including step definitions and helper classes.
- `resources/config`: Configuration files for the project.
- `resources/features`: Feature files for Cucumber scenarios.

## Configuration

Configure your test environment in the `config` directory, and specify the URLs, browsers, or any other settings in the configuration files.

Example: `config/config.json`

```json
{
    "activeEnvironment": "QA",
    "activebrowser": "chrome",
    "environments": {
        "QA": {
            "Portal1": "https://example-portal1-qa.com",
            "Portal2": "https://example-portal2-qa.com",
            "Portal3": "https://example-qa.com"
        },
        // ... (UAT, Smoke environments)
    }
}
```

## Helper Class

The `Helper` class in this project provides utility methods for interacting with web elements, handling waits, and other common tasks in Selenium automation. This class ensures a clean and organized approach to writing test scripts.

## How to Run

1. Clone the repository: 
2. Navigate to the project directory: 
3. Run tests: `mvn clean test`

## Test Reports

Test reports can be generated using the chosen Cucumber report plugin. Specify the plugin configuration in the `pom.xml` file.

## Contributing

Feel free to contribute to the project by opening issues or creating pull requests. Follow the contribution guidelines mentioned in CONTRIBUTING.md.

