package org.mcclone.web;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Administrator on 2017/7/30.
 */
public class JsoupTest {

    public static void main(String[] args) throws IOException {
        Document document = Jsoup.connect("http://www.dygang.net/").get();
        Elements element = document.select("#tab1_div_0 .c2");
        System.out.println(element.eachText());

    }
}
