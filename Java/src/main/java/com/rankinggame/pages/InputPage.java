package com.rankinggame.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class InputPage extends BasePage {

    // Locators based on the actual HTML structure
    private final By headingInput = By.id("headingInput");
    private final By inputTopicDescription = By.id("inputTopicDescription");
    private final By inputPageTopic = By.id("inputPageTopic");
    private final By inputPrompt = By.id("inputPrompt");
    private final By inputRows = By.cssSelector(".inputRow");
    private final By inputFields = By.cssSelector(".input-field-cell input");
    private final By removeButtons = By.cssSelector(".button.round-button.clear");
    private final By addButton = By.cssSelector(".addItem.rowButtons.round-button.add");
    private final By rankButton = By.id("RankBtn");
    private final By resetButton = By.cssSelector(".resetBtn");
    private final By volumeControl = By.cssSelector(".volume-control");
    private final By indexColumns = By.cssSelector(".index-column");

    /**
     * Constructor for the InputPage
     * @param driver WebDriver instance
     */
    public InputPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Gets the main heading text
     * @return Heading text
     */
    public String getHeadingText() {
        return getText(headingInput);
    }

    /**
     * Gets the topic label text
     * @return Topic label text
     */
    public String getTopicLabelText() {
        return getText(inputTopicDescription);
    }

    /**
     * Gets the current topic text
     * @return Current topic text
     */
    public String getCurrentTopic() {
        return getText(inputPageTopic);
    }

    /**
     * Gets the input prompt text
     * @return Input prompt text
     */
    public String getInputPromptText() {
        return getText(inputPrompt);
    }

    /**
     * Enters text in the specified input field (0-based index)
     * @param index Field index
     * @param text Text to enter
     */
    public void enterTextInInputField(int index, String text) {
        List<WebElement> fields = findElements(inputFields);
        if (index < fields.size()) {
            sendKeys(fields.get(index), text);
        } else {
            throw new IndexOutOfBoundsException("Input field at index " + index + " does not exist");
        }
    }

    /**
     * Clicks the add field button (+ button)
     */
    public void clickAddFieldButton() {
        click(addButton);
    }
    
    /**
     * Clicks the add field button using JavaScript to avoid element interception
     */
    public void clickAddFieldButtonJS() {
        clickJS(addButton);
    }

    /**
     * Clicks the remove field button for a specific row (0-based index)
     * @param index Button index
     */
    public void clickRemoveFieldButton(int index) {
        List<WebElement> buttons = findElements(removeButtons);
        if (index < buttons.size()) {
            click(buttons.get(index));
        } else {
            throw new IndexOutOfBoundsException("Remove button at index " + index + " does not exist");
        }
    }
    
    /**
     * Clicks the remove field button using JavaScript to avoid element interception
     * @param index Button index
     */
    public void clickRemoveFieldButtonJS(int index) {
        List<WebElement> buttons = findElements(removeButtons);
        if (index < buttons.size()) {
            clickJS(buttons.get(index));
        } else {
            throw new IndexOutOfBoundsException("Remove button at index " + index + " does not exist");
        }
    }

    /**
     * Fills all input fields with valid test data
     */
    public void fillAllInputFields() {
        List<WebElement> fields = findElements(inputFields);
        for (int i = 0; i < fields.size(); i++) {
            enterTextInInputField(i, "Item " + (i + 1));
        }
        sleep(500); // Wait for UI to update
    }

    /**
     * Clicks the Rank button to proceed
     */
    public void clickRankButton() {
        click(rankButton);
    }
    
    /**
     * Clicks the Rank button using JavaScript to avoid element interception
     */
    public void clickRankButtonJS() {
        clickJS(rankButton);
    }

    /**
     * Clicks the Reset button
     */
    public void clickResetButton() {
        click(resetButton);
    }

    /**
     * Toggles the volume (clicks the volume control)
     */
    public void toggleVolume() {
        click(volumeControl);
    }

    /**
     * Gets the total number of input fields
     * @return Number of input fields
     */
    public int getInputFieldCount() {
        return findElements(inputFields).size();
    }

    /**
     * Gets the row indices (the numbers displayed in the index column)
     * @return List of row indices as strings
     */
    public List<String> getRowIndices() {
        return getTexts(indexColumns);
    }

    /**
     * Waits for a specific number of input fields to be present
     * @param count Expected number of fields
     */
    public void waitForInputFieldCount(int count) {
        waitForElementCount(inputFields, count);
    }

    /**
     * Checks if the Rank button is enabled
     * @return true if enabled, false otherwise
     */
    public boolean isRankButtonEnabled() {
        return isButtonEnabled(rankButton);
    }

    /**
     * Gets the value of a specific input field (0-based index)
     * @param index Field index
     * @return Field value
     */
    public String getInputFieldValue(int index) {
        List<WebElement> fields = findElements(inputFields);
        if (index < fields.size()) {
            return getAttribute(fields.get(index), "value");
        } else {
            throw new IndexOutOfBoundsException("Input field at index " + index + " does not exist");
        }
    }
    
    /**
     * Gets the placeholder text of a specific input field (0-based index)
     * @param index Field index
     * @return Placeholder text
     */
    public String getInputFieldPlaceholder(int index) {
        List<WebElement> fields = findElements(inputFields);
        if (index < fields.size()) {
            return getAttribute(fields.get(index), "placeholder");
        } else {
            throw new IndexOutOfBoundsException("Input field at index " + index + " does not exist");
        }
    }
    
    /**
     * Waits for the page to be fully loaded
     */
    public void waitForPageToLoad() {
        waitForElementVisible(headingInput);
        waitForElementVisible(inputPrompt);
    }
    
    /**
     * Waits for the rank button to become enabled
     */
    public void waitForRankButtonEnabled() {
        waitForElementClickable(rankButton);
    }
}