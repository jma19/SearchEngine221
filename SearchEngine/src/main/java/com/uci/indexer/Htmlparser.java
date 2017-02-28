package com.uci.indexer;//package com.uci.parser;

import com.uci.mode.Document;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Htmlparser {

    private final static String lineSeparator = System.getProperty("line.separator");

    public static org.jsoup.nodes.Document load_url(String url) {
        org.jsoup.nodes.Document parseddoc = null;
        try {
            parseddoc = Jsoup.connect(url).get();
        } catch (Exception e) {
            System.out.println("Error on URL" + url);
            e.printStackTrace();
        }
        return parseddoc;
    }

    public static String getHtml(org.jsoup.nodes.Document document) {
        return document.html();
    }

    public static String getTitle(org.jsoup.nodes.Document document) {
        return document.title();
    }

    public static String getDescription(org.jsoup.nodes.Document document) {
        String description = "";
        Elements metaTags = document.select("meta[name=description]");
        if (metaTags != null && metaTags.size() > 0) {
            for (Element metaTag : metaTags) {
                description += metaTag.attr("content");
            }
        }
        return description;
    }

    public static String getKeywords(org.jsoup.nodes.Document document) {
        String keywords = "";
        Elements metaTags = document.select("meta[name=keywords]");
        if (metaTags != null && metaTags.size() > 0) {
            for (Element metaTag : metaTags) {
                keywords += metaTag.attr("content");
            }
        }
        return keywords;
    }

    public static String getBody(org.jsoup.nodes.Document document) {
        if (document.body() != null) {
            return document.body().text();
        }
        return "";
    }

    public static String getheaders(org.jsoup.nodes.Document document) {
        StringBuilder text = new StringBuilder();
        Elements elements = document.select("h1,h2,h3,h4,div[id*=title],div[class*=title],span[id*=title],span[class*=title]");
        if (elements != null) {
            for (Element element : elements) {
                text.append(element.text());
                text.append(lineSeparator);
            }
        }
        return text.toString();
    }

    public static String getImportantBody(org.jsoup.nodes.Document document) {
        StringBuilder text = new StringBuilder();
        Elements elements = document.select("b,strong,em");
        if (elements != null) {
            for (Element element : elements) {
                text.append(element.text());
                text.append(lineSeparator);
            }
        }
        return text.toString();
    }

    public static Document getDocumnet(String url) {
        org.jsoup.nodes.Document document = load_url(url);
        return new Document().setUrl(url).setTitle(getTitle(document)).setUrl(getBody(document));
    }
}
