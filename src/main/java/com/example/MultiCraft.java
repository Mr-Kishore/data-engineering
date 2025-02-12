package com.example;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import jdk.jfr.Category;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MultiCraft {


    public static void main(String[] args) throws IOException, InterruptedException {
        getCategories().forEach(categoryId -> {
            try {
                getProducts(categoryId);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static List<String> getCategories() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://multicraft.ca/en/brand"))
                .GET()
                .build();

        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        String htmlContent = httpResponse.body();
        Document htmlDocument = Jsoup.parse(htmlContent);

        Element ulElement = htmlDocument.selectFirst(".brandsList");

        List<String> categoryCodes = new ArrayList<>();

        if (ulElement != null) {
            Elements liElements = ulElement.select("li");

            for (Element li : liElements) {
                Element anchor = li.selectFirst("a[itemprop=url]"); // Get the anchor tag with itemprop="url"
                if (anchor != null) {
                    String href = anchor.attr("href"); // Get the href attribute value
                    String[] parts = href.split("code="); // Split at 'code='
                    if (parts.length > 1) {
                        String categoryCode = parts[1]; // Extract the category code
                        categoryCodes.add(categoryCode);
                    }
                }
            }
        }
        System.out.println("Extracted Categories: " + categoryCodes);
        return categoryCodes;
    }

    private static List<Product> getProducts(final String categoryId) throws IOException, InterruptedException {

        System.out.println("\n ------------------- Category: " + categoryId + " ------------------");
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://multicraft.ca/en/brand/subbrands?code=" + categoryId))
                .GET()
                .build();

        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        String htmlContent = httpResponse.body();

        Document htmlDocument = Jsoup.parse(htmlContent);

        Element ulElement = htmlDocument.selectFirst("#skusCards");
        if (ulElement == null) {
            System.out.println("No products found for category: " + categoryId);
            return new ArrayList<>();
        }

        Elements liElements = ulElement.select("li");

        List<Product> products = new ArrayList<>();

        liElements.forEach(liElement -> {

            String code = liElement.selectFirst(".summary-id").text();

            String title = liElement.selectFirst(".skuSummary-desc").selectFirst("p").text();

            products.add(new Product(code, title));

            System.out.println("Code: " + code + " Product: " + title);

        });
        return products;
    }

}