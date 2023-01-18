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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SearchTest {

    private static WebDriver driver;

    @BeforeAll
    public static void setUpDriver() {
        System.setProperty("webdriver.gecko.driver", "resources/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        driver = new ChromeDriver(options);
        // Implicity wait -> max czas na znalezienie elementu na stronie
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @BeforeEach
    public void setUp() throws Exception {
        driver.get("https://duckduckgo.com/");
    }

    @AfterAll
    public static void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void testTitlePage() {
        assertEquals("DuckDuckGo — Privacy, simplified.", driver.getTitle());
    }

    @Test
    public void testSource() {
        String source = driver.getPageSource();
        assertTrue(source.contains("DuckDuckGo"));
    }

    @Test
    public void testSearchResult() {

        driver.findElement(By.name("q")).sendKeys("selenium");

        driver.findElement(By.id("search_button_homepage")).click();

        WebElement result = driver.findElement(By.className("module__title__link"));

        assertEquals(result.getText(), "Selenium");

    }

    @Test
    public void testThirdElement() {
        driver.findElement(By.name("q")).sendKeys("selenium");

        driver.findElement(By.id("search_button_homepage")).submit();

        WebElement results = driver.findElement(By.cssSelector(".results.js-results"));

        List<WebElement> resultList = results.findElements(By.tagName("div"));

        assertNotNull(resultList.get(2));

    }

    @Test
    public void testNoElement() throws InterruptedException {
        driver.findElement(By.name("q")).sendKeys("asdaaaaaaaaaaaaasdasdasdasdasdxcvxcvxcvsdfsdfsdfsdfsdfsfsdf");

        driver.findElement(By.id("search_button_homepage")).submit();

        WebElement results = driver.findElement(By.cssSelector(".results.js-results"));

        List<WebElement> resultList = results.findElements(By.tagName("div"));

        assertNotNull(resultList.get(0));

    }

}