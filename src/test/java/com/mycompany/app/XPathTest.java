package com.mycompany.app;

import static org.junit.Assert.*;

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

public class XPathTest {

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
    public void testLinks() {
        driver.get("https://duckduckgo.com/?q=Cats&t=h_&ia=web");
        List<WebElement> links = driver.findElements(By.xpath("//a"));
        assertNotNull(links.size());
    }

    @Test
    public void testImages() {
        driver.get("https://duckduckgo.com/?q=Cats&t=h_&iax=images&ia=images");
        List<WebElement> links = driver.findElements(By.xpath("//img"));
        assertNotNull(links.size());
    }

    @Test
    public void testLinksVisit() {

        driver.get("https://duckduckgo.com/?q=Cats&t=h_&ia=web");
        List<WebElement> links = driver.findElements(By.xpath("//a"));
        int size = links.size();

        for (int i = 0; i <= size; i++) {
            if (!(links.get(i).getText().isEmpty())) {
                links.get(i).click();
                driver.navigate().back();
                links = driver.findElements(By.tagName("a"));
                size = links.size();
            }
        }
        assertNotNull(links.size());
    }

    @Test
    public void testFields() {

        driver.get("https://accounts.spotify.com/en/login");
        List<WebElement> textfield = driver.findElements(By.xpath("//input[@type='text' or @type='password']"));
        assertEquals(2, textfield.size());

    }
}
