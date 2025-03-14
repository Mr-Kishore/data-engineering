package com.example;

import java.io.IOException;
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

public class houseWare {
    public static void main(String[] args) throws IOException, InterruptedException {
        String categoryId = "glassware";

        getProducts(categoryId);

    }

    private static List<Product> getProducts(String categoryId) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://modernhouseware.com/product-category/" + categoryId + "/"))
                .GET()
                .build();

        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        String htmlContent = httpResponse.body();

        List<Product> products = new ArrayList<>();

        Document htmlDocument = Jsoup.parse(htmlContent);

        Elements ulElements = htmlDocument.select(".products");
        int count = 1;

        for (Element ul : ulElements) {
            Elements liElements = ul.select("li");

            for (Element product : liElements) {
                String title = product.select(".woocommerce-loop-product__title").text();
                String code = product.select(".prod_number").text();

                System.out.println(count + ". Code: " + code + " Product Name: " + title);
                products.add(new Product(code, title));

                count++;
            }
        }
        return products;
    }
}
