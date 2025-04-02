package com.rankinggame.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    
    /**
     * Constructor for BasePage
     * @param driver WebDriver instance
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    /**
     * Wait for element to be visible
     * @param locator By locator
     * @return WebElement
     */
    protected WebElement waitForElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    /**
     * Wait for element to be visible
     * @param element WebElement
     * @return WebElement
     */
    protected WebElement waitForElementVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Wait for element to be clickable
     * @param locator By locator
     * @return WebElement
     */
    protected WebElement waitForElementClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    /**
     * Wait for element to be clickable
     * @param element WebElement
     * @return WebElement
     */
    protected WebElement waitForElementClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    
    /**
     * Wait for specific number of elements
     * @param locator By locator
     * @param count expected number of elements
     */
    protected void waitForElementCount(By locator, int count) {
        wait.until(ExpectedConditions.numberOfElementsToBe(locator, count));
    }

    /**
     * Click element
     * @param locator By locator
     */
    protected void click(By locator) {
        waitForElementClickable(locator).click();
    }
    
    /**
     * Click element
     * @param element WebElement
     */
    protected void click(WebElement element) {
        waitForElementClickable(element).click();
    }
    
    /**
     * Click element using JavaScript (helps with element interception)
     * @param locator By locator
     */
    protected void clickJS(By locator) {
        WebElement element = driver.findElement(locator);
        clickJS(element);
    }
    
    /**
     * Click element using JavaScript (helps with element interception)
     * @param element WebElement
     */
    protected void clickJS(WebElement element) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
        jsExecutor.executeScript("arguments[0].click();", element);
        // Wait briefly for any resulting actions
        sleep(500);
    }

    /**
     * Enter text into element after clearing it
     * @param locator By locator
     * @param text Text to enter
     */
    protected void sendKeys(By locator, String text) {
        WebElement element = waitForElementVisible(locator);
        element.clear();
        element.sendKeys(text);
    }
    
    /**
     * Enter text into element after clearing it
     * @param element WebElement
     * @param text Text to enter
     */
    protected void sendKeys(WebElement element, String text) {
        waitForElementVisible(element);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Get text from element
     * @param locator By locator
     * @return Text content
     */
    protected String getText(By locator) {
        return waitForElementVisible(locator).getText();
    }
    
    /**
     * Get text from element
     * @param element WebElement
     * @return Text content
     */
    protected String getText(WebElement element) {
        return waitForElementVisible(element).getText();
    }
    
    /**
     * Get attribute value from element
     * @param locator By locator
     * @param attribute Attribute name
     * @return Attribute value
     */
    protected String getAttribute(By locator, String attribute) {
        return waitForElementVisible(locator).getAttribute(attribute);
    }
    
    /**
     * Get attribute value from element
     * @param element WebElement
     * @param attribute Attribute name
     * @return Attribute value
     */
    protected String getAttribute(WebElement element, String attribute) {
        return waitForElementVisible(element).getAttribute(attribute);
    }

    /**
     * Check if element is displayed
     * @param locator By locator
     * @return true if displayed, false otherwise
     */
    protected boolean isElementDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Check if element is displayed
     * @param element WebElement
     * @return true if displayed, false otherwise
     */
    protected boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Check if element is enabled
     * @param locator By locator
     * @return true if enabled, false otherwise
     */
    protected boolean isElementEnabled(By locator) {
        try {
            return driver.findElement(locator).isEnabled();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Check if element is enabled
     * @param element WebElement
     * @return true if enabled, false otherwise
     */
    protected boolean isElementEnabled(WebElement element) {
        try {
            return element.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Check if element has class
     * @param element WebElement
     * @param className Class name to check
     * @return true if element has class, false otherwise
     */
    protected boolean hasClass(WebElement element, String className) {
        String classes = getAttribute(element, "class");
        return classes != null && classes.contains(className);
    }
    
    /**
     * Find all elements matching locator
     * @param locator By locator
     * @return List of WebElements
     */
    protected List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }
    
    /**
     * Get text from multiple elements
     * @param locator By locator for multiple elements
     * @return List of strings containing element texts
     */
    protected List<String> getTexts(By locator) {
        return findElements(locator).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
    
    /**
     * Sleep for specified milliseconds
     * @param millis Time in milliseconds
     */
    protected void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Check if button is enabled by checking for disabled attribute
     * @param element Button element
     * @return true if enabled, false if disabled
     */
    protected boolean isButtonEnabled(WebElement element) {
        String disabled = element.getAttribute("disabled");
        return disabled == null || disabled.equals("false");
    }
    
    /**
     * Check if button is enabled by checking for disabled attribute
     * @param locator Button locator
     * @return true if enabled, false if disabled
     */
    protected boolean isButtonEnabled(By locator) {
        WebElement element = driver.findElement(locator);
        return isButtonEnabled(element);
    }
    
    /**
     * Wait for page to load
     * @param titleLocator Locator for an element that indicates page is loaded
     */
    protected void waitForPageToLoad(By titleLocator) {
        waitForElementVisible(titleLocator);
    }
}