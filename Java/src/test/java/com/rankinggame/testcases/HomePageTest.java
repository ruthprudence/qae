package com.rankinggame.testcases;

import com.rankinggame.pages.HomePage;
import com.rankinggame.utils.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class HomePageTest {

    private WebDriver driver;
    private HomePage homePage;
    private final String HOME_PAGE_URL = "https://rg.ruthprudence.com";

    @BeforeMethod
    public void setup() {
        driver = DriverManager.getDriver();
        homePage = new HomePage(driver);
        driver.get(HOME_PAGE_URL);
    }

    @Test
    public void testHomePageLoads() {
        // Verify the page loads
        Assert.assertTrue(driver.getCurrentUrl().contains(HOME_PAGE_URL)); // Using contains()
        Assert.assertTrue(driver.getTitle().contains("Ranking Game")); // Verify the page title
    }

    // @Test
    // public void testAppDivIsVisible() {
    //     // Verify the "App" div is visible
    //     WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    //     WebElement appDiv = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("App")));
    //     Assert.assertTrue(appDiv.isDisplayed());
    // }

    @Test
    public void testInputFieldIsVisibleAndInteractable() {
        // Verify the input field is visible and interactable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("topicInput")));
        Assert.assertTrue(inputField.isDisplayed());

        // Enter text into the input field
        inputField.sendKeys("Sample Ranking");
        Assert.assertEquals(inputField.getAttribute("value"), "Sample Ranking");

        // Clear the input field using the clear button
        WebElement clearButton = driver.findElement(By.cssSelector(".splashClearTopicButton.round-button.clear"));
        clearButton.click();
        Assert.assertEquals(inputField.getAttribute("value"), ""); // Verify the field is cleared
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }
}