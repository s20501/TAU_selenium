package com.mycompany.app;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class LoginFormTest {
    private static WebDriver driver;
    private static ChromeOptions options;

    @BeforeAll
    public static void setUpDriver() {
        System.setProperty("webdriver.gecko.driver", "resources/chromedriver");
        options = new ChromeOptions();
        options.setHeadless(true);
        driver = new ChromeDriver(options);
        // Implicity wait -> max czas na znalezienie elementu na stronie
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @BeforeEach
    public void setUp() throws Exception {
        driver.quit();
        driver = new ChromeDriver(options);
        // Implicity wait -> max czas na znalezienie elementu na stronie
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://accounts.spotify.com/en/login");
    }

    @AfterAll
    public static void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void testTitlePage() {
        assertEquals("Login - Spotify", driver.getTitle());
    }

    @Test
    public void testLoginSuccess() {

        driver.findElement(By.id("login-username")).sendKeys("tma22637@omeie.com");

        driver.findElement(By.id("login-password")).sendKeys("f6JqA!B@3upeT6^Q");

        driver.findElement(By.id("login-button")).click();

        WebElement result = driver.findElement(By.cssSelector("[data-testid=user-info]"));

        assertEquals(result.findElement(By.tagName("p")).getText(), "Logged in as sdasdsad.");

    }

    @Test
    public void testLoginFail() {

        driver.findElement(By.id("login-username")).sendKeys("tma22637@omeie.com");

        driver.findElement(By.id("login-password")).sendKeys("test");

        driver.findElement(By.id("login-button")).click();

        WebElement result = driver.findElement(By.className("encore-negative-set"));

        assertEquals(result.findElement(By.tagName("span")).getText(), "Incorrect username or password.");

    }

    @Test
    public void testValidationEmail() {

        driver.findElement(By.id("login-username")).sendKeys("t");

        driver.findElement(By.id("login-username")).sendKeys(Keys.BACK_SPACE);

        driver.findElement(By.id("login-password")).sendKeys("test");

        WebElement result = driver.findElement(By.id("username-error"));

        assertEquals(result.findElement(By.tagName("p")).getText(),
                "Please enter your Spotify username or email address.");

    }

    @Test
    public void testValidationPassword() {

        driver.findElement(By.id("login-password")).sendKeys("t");

        driver.findElement(By.id("login-password")).sendKeys(Keys.BACK_SPACE);

        WebElement result = driver.findElement(By.id("password-error"));

        assertEquals(result.getText(),
                "Please enter your password.");

    }

}
