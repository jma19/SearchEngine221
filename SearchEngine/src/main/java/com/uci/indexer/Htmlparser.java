package com.uci.indexer;//package com.uci.parser;

import com.uci.mode.Document;
import com.uci.utils.UrlChecker;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

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

    public static Document loadDocument(String url) {
        org.jsoup.nodes.Document document = load_url(url);
        return new Document().setTitle(getBody(document)).setTitle(getTitle(document)).setUrl(url);
    }

    public static Document generateDocument(org.jsoup.nodes.Document doc, String url) {
        try {
            return new Document().setTitle(getTitle(doc)).setBody(getBody(doc)).setUrl(url);
        } catch (Exception exp) {
            System.out.println(String.format("Parsing html file failed with url = %s!!!", url));
            exp.printStackTrace();
        }
        return null;
    }

    public static Map<String, String> getOutgoingLinks(org.jsoup.nodes.Document doc, String url) {
        Map<String, String> outgoingLinks = new HashMap();
        Elements links = doc.select("a");
        if (links != null) {
            for (Element link : links) {
                String href = link.attr("href");
                if (href.contains("javascript:") || href.contains("mailto:") || href.contains("https"))
                    continue;

                String absoluteHref = combineUrls(url, href);
                if(!UrlChecker.isValid(absoluteHref)){
                    continue;
                }

                String currentAnchorText = outgoingLinks.get(absoluteHref);
                if (currentAnchorText == null)
                    currentAnchorText = "";
                currentAnchorText += " " + link.text();
                outgoingLinks.put(absoluteHref, currentAnchorText.trim());
            }
        }
        return outgoingLinks;
    }

    private static String combineUrls(String url, String relativeUrl) {
        if (relativeUrl.contains("http"))
            return relativeUrl;
        try {
            URI baseUri = new URI(url);
            return baseUri.resolve(new URI(relativeUrl)).toString();
        } catch (URISyntaxException e) {
            System.out.println("Error on URL: (" + url + ", " + relativeUrl + ")");
        }
        return null;
    }

    private static String getText(org.jsoup.nodes.Document doc) {
        return new StringBuffer().append(getDescription(doc))
                .append("#")
                .append(getheaders(doc))
                .append("#")
                .append(getBody(doc)).toString();
    }
}
