Certainly! Here's a basic Markdown file template for documenting the usage of your `Helper` class:

# Helper Class Documentation

## Overview

The `Helper` class is designed to facilitate Selenium WebDriver operations and streamline test automation. This documentation provides a guide on how to use the various methods and functionalities offered by the class.

## Table of Contents

- [Helper Class Documentation](#helper-class-documentation)
  - [Overview](#overview)
  - [Table of Contents](#table-of-contents)
  - [Installation](#installation)
  - [Initialization](#initialization)
  - [WebDriver Operations](#webdriver-operations)
    - [Clicking Elements](#clicking-elements)
    - [Typing Text](#typing-text)
    - [Getting Text](#getting-text)
    - [Checking Element Display](#checking-element-display)
    - [Waiting for Element Visibility](#waiting-for-element-visibility)
    - [Taking Screenshots](#taking-screenshots)
    - [Selecting Options from Dropdown](#selecting-options-from-dropdown)
    - [Mouse Hover Actions](#mouse-hover-actions)
    - [Right-Click Actions](#right-click-actions)
    - [Double-Click Actions](#double-click-actions)
    - [Switching Frames and Windows](#switching-frames-and-windows)
    - [Clearing Browser Cookies](#clearing-browser-cookies)
    - [Assertions](#assertions)
  - [Alert Handling](#alert-handling)
    - [Accepting Alerts](#accepting-alerts)
    - [Dismissing Alerts](#dismissing-alerts)
    - [Getting Alert Text](#getting-alert-text)
  - [File Operations](#file-operations)
    - [Uploading Files](#uploading-files)
    - [Downloading Files](#downloading-files)
  - [Miscellaneous](#miscellaneous)
    - [Getting Current URL](#getting-current-url)
    - [Scrolling to Elements](#scrolling-to-elements)

## Installation

The `Helper` class is part of your Selenium WebDriver test automation project. Ensure that the necessary Selenium WebDriver dependencies are added to your project.

## Initialization

To use the `Helper` class, you need to initialize an instance of it and set up the WebDriver. Here's an example:

```java
Helper helper = new Helper();
helper.initializeDriver("chrome", "path/to/chromedriver");
```

Replace `"chrome"` with the desired browser type (e.g., `"firefox"`) and provide the correct path to the WebDriver executable.

## WebDriver Operations

### Clicking Elements

Use the `click` method to click on a web element:

```java
helper.click(By.id("elementId"));
```

### Typing Text

Use the `typeText` method to type text into a web element:

```java
helper.typeText(By.name("username"), "yourUsername");
```

### Getting Text

Use the `getText` method to retrieve text from a web element:

```java
String elementText = helper.getText(By.cssSelector(".example"));
```

### Checking Element Display

Use the `isElementDisplayed` method to check if a web element is displayed:

```java
boolean isDisplayed = helper.isElementDisplayed(By.className("visible"));
```

### Waiting for Element Visibility

Use the `waitForElementToBeVisible` method to wait for an element to be visible:

```java
helper.waitForElementToBeVisible(By.xpath("//div[@id='loading']"), Duration.ofSeconds(10));
```

### Taking Screenshots

Capture a screenshot using the `takeScreenshot` method:

```java
helper.takeScreenshot("testScreenshot");
```

### Selecting Options from Dropdown

Use the `selectOptionFromDropdown` method to select an option from a dropdown:

```java
helper.selectOptionFromDropdown(By.id("dropdown"), "Option 1");
```

### Mouse Hover Actions

Perform a mouse hover using the `performMouseHover` method:

```java
helper.performMouseHover(By.linkText("Hover Me"));
```

### Right-Click Actions

Perform a right-click using the `rightClick` method:

```java
helper.rightClick(By.id("contextMenu"));
```

### Double-Click Actions

Perform a double-click using the `doubleClick` method:

```java
helper.doubleClick(By.id("doubleClickMe"));
```

### Switching Frames and Windows

Switch to a frame or window using the respective methods:

```java
helper.switchToFrame(By.name("frameName"));
helper.switchToWindow("windowHandle");
```

### Clearing Browser Cookies

Clear browser cookies using the `clearCookies` method:

```java
helper.clearCookies();
```

### Assertions

Assert text equality or element display using assertion methods:

```java
helper.assertTextEquals(actualText, expectedText, "Texts are not equal");
helper.assertElementDisplayed(By.cssSelector(".visible"), "Element is not displayed");
```

## Alert Handling

### Accepting Alerts

Use the `acceptAlert` method to accept an alert:

```java
helper.acceptAlert();
```

### Dismissing Alerts

Use the `dismissAlert` method to dismiss an alert:

```java
helper.dismissAlert();
```

### Getting Alert Text

Retrieve text from an alert using the `getAlertText` method:

```java
String alertText = helper.getAlertText();
```

## File Operations

### Uploading Files

Use the `uploadFile` method to upload a file:

```java
helper.uploadFile(By.id("fileInput"), "path/to/file.txt");
```

### Downloading Files

Implement file download logic as needed in the `downloadFile` method.

## Miscellaneous

### Getting Current URL

Retrieve the current URL using the `getCurrentURL` method:

```java
String currentURL = helper.getCurrentURL();
```

### Scrolling to Elements

Scroll to a specific element using the `scrollToElement` method:

```java
helper.scrollToElement(By.id("scrollTarget"));
```

---

Feel free to adapt and expand this documentation based on your specific project requirements.
```

This template provides a structured guide for users to understand the functionalities and usage of the `Helper` class. You can customize it further based on your specific project requirements.