package com.example;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class JsoupExample {
    public static void main(String[] args) {
        try {
            Document doc = Jsoup.connect("https://example.com").get();
            System.out.println(doc.title());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
