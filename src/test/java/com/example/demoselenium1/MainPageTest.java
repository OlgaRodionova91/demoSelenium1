package com.example.demoselenium1;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MainPageTest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        // Fix the issue https://github.com/SeleniumHQ/selenium/issues/11750
        options.addArguments("--remote-allow-origins=*");
        System.setProperty("webdriver.chrome.driver", "D:\\Папка Оли\\тестировщик\\chromedriver.exe");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.bing.com/");

    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void search() {
        String input = "Selenium";
        WebElement searchField = driver.findElement(By.cssSelector("#sb_form_q"));
        searchField.sendKeys(input);
        searchField.submit();

        WebElement searchPageField = driver.findElement(By.cssSelector("#sb_form_q"));
        assertEquals(input, searchPageField.getAttribute("value"), "Значение в поле ввода поменялось");
    }



    @Test
    public void searchSelenium() {
        String input = "Selenium";

        WebElement searchField = driver.findElement(By.cssSelector("#sb_form_q"));
        searchField.sendKeys(input);
        searchField.submit();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));
        wait.until(ExpectedConditions.and(
                ExpectedConditions.attributeContains(By.cssSelector(":not(.b.adurl)>cite"), "cite", "selenium"),
                ExpectedConditions.elementToBeClickable(By.cssSelector(":not(.b.adurl)>cite"))
        ));
        List<WebElement> results = driver.findElements(By.cssSelector(":not(.b.adurl)>cite"));
        //results.get(0).click();

        clickElement(results, 0);
        String link = driver.getCurrentUrl();
        assertEquals("https://www.selenium.dev/", link, "Адреса не совпадают");

        for (WebElement el : results) {
            System.out.println(el.getText());
        }
    }

    public void clickElement(List<WebElement> results, int num) {
        results.get(num).click();
        System.out.println("Клик по номеру" + num);
    }

}


