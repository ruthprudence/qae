package com.rankinggame.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    @FindBy(id = "username")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "loginButton")
    private WebElement loginButton;

    @FindBy(css = ".error-message")
    private WebElement errorMessage;

    private By loginFormLocator = By.id("loginForm");

    public HomePage(WebDriver driver) {
        super(driver); // Call the BasePage constructor
    }

    public void navigateToLoginPage(String url) {
        driver.get(url);
        waitForElementVisible(loginFormLocator);
    }

    public void enterUsername(String username) {
        usernameField.clear();
        usernameField.sendKeys(username);
    }

    public void enterPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void clickLoginButton() {
        loginButton.click();
    }

    public HomePage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        return new HomePage(driver);
    }

    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(By.cssSelector(".error-message"));
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }
}