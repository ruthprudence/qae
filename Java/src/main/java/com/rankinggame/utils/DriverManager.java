package com.rankinggame.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;

import java.time.Duration;

public class DriverManager {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private DriverManager() {
        // Prevent instantiation
    }

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            initializeDriver();
        }
        return driver.get();
    }

    public static void initializeDriver() {
        String browser = ConfigReader.getProperty("browser").toLowerCase(); // Corrected line
        boolean headless = Boolean.parseBoolean(ConfigReader.getProperty("headless")); // Corrected line

        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) chromeOptions.addArguments("--headless=new");
                driver.set(new ChromeDriver(chromeOptions));
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) firefoxOptions.addArguments("--headless");
                driver.set(new FirefoxDriver(firefoxOptions));
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) edgeOptions.addArguments("--headless");
                driver.set(new EdgeDriver(edgeOptions));
                break;

            case "safari":
                driver.set(new SafariDriver());
                break;

            default:
                throw new IllegalArgumentException("Browser " + browser + " is not supported");
        }

        // Configure default timeouts
        WebDriver currentDriver = driver.get();
        if (currentDriver != null) {
            currentDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            currentDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            currentDriver.manage().window().maximize();
        }
    }

    public static void quitDriver() {
        WebDriver currentDriver = driver.get();
        if (currentDriver != null) {
            currentDriver.quit();
            driver.remove();
        }
    }
}