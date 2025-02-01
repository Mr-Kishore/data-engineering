package com.example;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MultiCraft {
    public static void main(String[] args) throws IOException, InterruptedException {

        final String url = "https://multicraft.ca/en/brand/subbrands?code=artscrafts";

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest httpRequest =  HttpRequest.newBuilder()
                .uri(URI.create(url)).GET().build();

        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        String htmlContent = httpResponse.body();

        Document htmlDocument = Jsoup.parse(htmlContent);

        Element ulElement = htmlDocument.selectFirst("#skusCards");

        Elements liElements = ulElement.select("li");
        List<Product> products = new ArrayList<>();
        liElements.forEach(liElement -> {

            String code = liElement.selectFirst(".summary-id").text();

            String title = liElement.selectFirst(".skuSummary-desc").selectFirst("p").text();
            System.out.println("Code: " + code + " Product: " + title);

            products.add(new Product(code, title));
        });


    }

}
