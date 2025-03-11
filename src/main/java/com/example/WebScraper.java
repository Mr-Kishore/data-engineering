package com.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class WebScraper {
    public static void main(String[] args) {
        // Setup Chrome WebDriver
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            // Open the website using Selenium
            String url = "https://www.example.com/";
            driver.get(url);

            // Get the page source
            String pageSource = driver.getPageSource();

            // Parse HTML with JSoup
            Document doc = Jsoup.parse(pageSource);

            // Extract and print page title
            String title = doc.title();
            System.out.println("Page Title: " + title);

            // Extract and print body text
            String bodyText = doc.body().text();
            System.out.println("Page Content: " + bodyText);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}
