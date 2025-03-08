package com.example;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MultiCraft {
    public static void main(String[] args) throws IOException, InterruptedException {

        final String url = "https://multicraft.ca/en/brand/subbrands?code=artscrafts";

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest httpRequest =  HttpRequest.newBuilder()
                .uri(URI.create(url)).GET().build();

        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        System.out.println(httpResponse.body());
    }
}
