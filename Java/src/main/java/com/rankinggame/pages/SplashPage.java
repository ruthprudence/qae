package com.rankinggame.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SplashPage extends BasePage {

    // Page title and prompt elements
    @FindBy(id = "headingInput")
    private WebElement headingTitle;

    @FindBy(id = "splashPrompt")
    private WebElement splashPrompt;
    
    @FindBy(className = "splashPromptContainer")
    private WebElement splashPromptContainer;

    // Input field and related buttons
    @FindBy(id = "topicInput")
    private WebElement topicInputField;

    @FindBy(css = ".splashClearTopicButton.round-button.clear")
    private WebElement clearTopicButton;

    @FindBy(id = "submitTopicButton")
    private WebElement submitTopicButton;

    // Audio elements
    private final By audioElements = By.tagName("audio");
    
    // Footer elements
    @FindBy(className = "volume-control")
    private WebElement volumeControl;

    @FindBy(css = ".footer .copyright")
    private WebElement copyright;

    // Animation containers
    @FindBy(className = "slideLeftToRight")
    private WebElement slideLeftToRightContainer;

    @FindBy(className = "slideBottomToTop")
    private WebElement slideBottomToTopContainer;

    /**
     * Constructor for the SplashPage
     * @param driver WebDriver instance
     */
    public SplashPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Enters a topic in the input field
     * @param topic The topic to enter
     */
    public void enterTopic(String topic) {
        sendKeys(topicInputField, topic);
    }

    /**
     * Clears the topic input field using the clear button
     */
    public void clearTopic() {
        click(clearTopicButton);
    }

    /**
     * Clicks the submit topic button
     */
    public void clickSubmitTopic() {
        click(submitTopicButton);
    }

    /**
     * Checks if the submit button is enabled
     * @return true if enabled, false otherwise
     */
    public boolean isSubmitButtonEnabled() {
        return isButtonEnabled(submitTopicButton);
    }

    /**
     * Gets the current value of the topic input field
     * @return The current topic input value
     */
    public String getTopicInputValue() {
        return getAttribute(topicInputField, "value");
    }

    /**
     * Gets the placeholder text from the topic input field
     * @return The placeholder text
     */
    public String getTopicInputPlaceholder() {
        return getAttribute(topicInputField, "placeholder");
    }

    /**
     * Gets the text from the heading element
     * @return The heading text
     */
    public String getHeadingText() {
        return getText(headingTitle);
    }
    
    /**
     * Gets the text from the splash prompt element
     * @return The splash prompt text
     */
    public String getSplashPromptText() {
        return getText(splashPrompt);
    }

    /**
     * Checks if the splash prompt has the marquee animation class
     * @return true if the animation is present, false otherwise
     */
    public boolean hasSplashPromptMarqueeAnimation() {
        return hasClass(splashPrompt, "marquee-animation");
    }

    /**
     * Gets the text from all copyright elements in the footer
     * @return Array of copyright text strings
     */
    public String[] getCopyrightTexts() {
        return getTexts(By.cssSelector(".footer .copyright")).toArray(new String[0]);
    }

    /**
     * Toggles the volume by clicking the volume control element
     */
    public void toggleVolume() {
        click(volumeControl);
    }

    /**
     * Checks if an audio element exists and is loaded
     * @param audioId The ID of the audio element to check
     * @return true if the audio element exists and is loaded, false otherwise
     */
    public boolean isAudioElementLoaded(String audioId) {
        try {
            WebElement audioElement = driver.findElement(By.id(audioId));
            return audioElement != null && "auto".equals(getAttribute(audioElement, "preload"));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Waits for the submit button to become enabled
     */
    public void waitForSubmitButtonEnabled() {
        waitForElementClickable(submitTopicButton);
    }

    /**
     * Checks if the submit button has the disabled class
     * @return true if disabled, false otherwise
     */
    public boolean hasSubmitButtonDisabledClass() {
        return hasClass(submitTopicButton, "submitTopicButtonDisabled");
    }

    /**
     * Waits for the left-to-right slide animation container to be visible
     */
    public void waitForLeftToRightSlideVisible() {
        waitForElementVisible(slideLeftToRightContainer);
    }

    /**
     * Waits for the bottom-to-top slide animation container to be visible
     */
    public void waitForBottomToTopSlideVisible() {
        waitForElementVisible(slideBottomToTopContainer);
    }
}