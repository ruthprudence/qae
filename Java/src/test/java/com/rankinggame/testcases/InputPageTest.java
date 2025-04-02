package com.rankinggame.testcases;

import com.rankinggame.pages.InputPage;
import com.rankinggame.pages.SplashPage;
import com.rankinggame.utils.DriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class InputPageTest {

    private WebDriver driver;
    private InputPage inputPage;
    private SplashPage splashPage;
    private JavascriptExecutor jsExecutor;

    @BeforeMethod
    public void setup() {
        driver = DriverManager.getDriver();
        jsExecutor = (JavascriptExecutor) driver;
        inputPage = new InputPage(driver);
        splashPage = new SplashPage(driver);
        driver.get("https://rg.ruthprudence.com");
        splashPage.enterTopic("Sample Topic");
        splashPage.waitForSubmitButtonEnabled();
        splashPage.clickSubmitTopic();
        inputPage.waitForPageToLoad();
    }

    @Test
    public void testPageElements() {
        Assert.assertEquals(inputPage.getHeadingText(), "the Ranking Game");
        Assert.assertEquals(inputPage.getTopicLabelText(), "Your Topic:");
        Assert.assertEquals(inputPage.getCurrentTopic(), "Sample Topic");
        Assert.assertEquals(inputPage.getInputPromptText(), "Enter between 3 and 12 items.");
    }

    @Test
    public void testInitialInputFields() {
        Assert.assertEquals(inputPage.getInputFieldCount(), 3);
        Assert.assertEquals(inputPage.getInputFieldValue(0), "");
        Assert.assertEquals(inputPage.getInputFieldValue(1), "");
        Assert.assertEquals(inputPage.getInputFieldValue(2), "");
        
        // Test row indices
        Assert.assertEquals(inputPage.getRowIndices().get(0), "1");
        Assert.assertEquals(inputPage.getRowIndices().get(1), "2");
        Assert.assertEquals(inputPage.getRowIndices().get(2), "3");
    }

    @Test
    public void testInputFieldPlaceholders() {
        // Verify placeholders contain examples
        Assert.assertTrue(inputPage.getInputFieldPlaceholder(0).contains("e.g."));
        Assert.assertTrue(inputPage.getInputFieldPlaceholder(1).contains("e.g."));
        Assert.assertTrue(inputPage.getInputFieldPlaceholder(2).contains("e.g."));
    }

    @Test
    public void testAddField() {
        int initialCount = inputPage.getInputFieldCount();
        // Use JavaScript to click the add button to avoid element interception
        inputPage.clickAddFieldButtonJS();
        inputPage.waitForInputFieldCount(initialCount + 1);
        Assert.assertEquals(inputPage.getInputFieldCount(), initialCount + 1);
        // Verify new row index
        Assert.assertEquals(inputPage.getRowIndices().get(initialCount), String.valueOf(initialCount + 1));
    }

    @Test
    public void testRemoveField() {
        // First add an extra field to ensure we have at least 4
        inputPage.clickAddFieldButtonJS();
        int initialCount = inputPage.getInputFieldCount();
        
        // Now remove one field
        inputPage.clickRemoveFieldButtonJS(1);
        
        // Wait for the UI to update and check the new count directly
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        int newCount = inputPage.getInputFieldCount();
        Assert.assertEquals(newCount, initialCount - 1, 
            "Expected field count to decrease from " + initialCount + " to " + (initialCount - 1) + 
            " but got " + newCount);
    }

    @Test
    public void testInputFieldsAndRankButton() {
        // Fill all input fields
        inputPage.fillAllInputFields();
        
        // Check if rank button is enabled after filling all fields
        boolean isEnabled = inputPage.isRankButtonEnabled();
        Assert.assertTrue(isEnabled, "Rank button should be enabled after filling all fields");
        
        // Use JavaScript to click the rank button
        inputPage.clickRankButtonJS();
        
        // Verify navigation to next page
        Assert.assertTrue(driver.getCurrentUrl().contains("/matchup"), 
            "URL should contain '/matchup' after clicking Rank button");
    }

    @Test
    public void testRankButtonDisabledByDefault() {
        Assert.assertFalse(inputPage.isRankButtonEnabled(), 
            "Rank button should be disabled by default");
    }

    @Test
    public void testRankButtonEnabledAfterInput() {
        // Fill all fields
        inputPage.fillAllInputFields();
        
        // Check if button is enabled
        Assert.assertTrue(inputPage.isRankButtonEnabled(), 
            "Rank button should be enabled after filling all fields");
    }

    @Test
    public void testRankButtonDisabledWithEmptyFields() {
        // Fill all fields first
        inputPage.fillAllInputFields();
        
        // Verify button is enabled
        Assert.assertTrue(inputPage.isRankButtonEnabled(), 
            "Rank button should be enabled after filling all fields");
        
        // Then clear one field
        inputPage.enterTextInInputField(1, "");
        
        // Wait briefly for UI to update
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Button should be disabled again
        boolean buttonState = inputPage.isRankButtonEnabled();
        Assert.assertFalse(buttonState, 
            "Rank button should be disabled when a field is empty");
    }

    @Test
    public void testMaximumFields() {
        // Add fields until we reach 12 (which is the max according to the prompt)
        int initialCount = inputPage.getInputFieldCount();
        for (int i = 0; i < 9; i++) { // Adding 9 more to reach 12 total
            inputPage.clickAddFieldButtonJS();
            // Wait briefly after each addition
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        // Check if we have 12 fields now
        Assert.assertEquals(inputPage.getInputFieldCount(), 12, 
            "Should have 12 input fields after adding 9 to the initial 3");
    }

    @Test
    public void testResetButton() {
        // Enter some data
        inputPage.enterTextInInputField(0, "Item 1");
        inputPage.enterTextInInputField(1, "Item 2");
        
        // Click reset button - this should navigate back to splash page
        inputPage.clickResetButton();
        
        // Verify we're back at the splash page
        Assert.assertTrue(driver.getCurrentUrl().contains("/"), 
            "URL should be the root path after clicking Reset");
        Assert.assertFalse(driver.getCurrentUrl().contains("/input"), 
            "URL should not contain '/input' after clicking Reset");
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }
}