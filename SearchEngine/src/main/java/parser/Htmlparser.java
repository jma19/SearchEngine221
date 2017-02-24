package parser;

import mode.DocumentEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Htmlparser {

    private final static String lineSeparator = System.getProperty("line.separator");

    public static Document load_url(String url) {
        Document parseddoc = null;
        try {
            parseddoc = Jsoup.connect(url).get();
        } catch (Exception e) {
            System.out.println("Error on URL" + url);
            e.printStackTrace();
        }
        return parseddoc;
    }

    public static String getHtml(Document document) {
        return document.html();
    }

    public static String getTitle(String url) {
        Document doc = load_url(url);
        return doc.title();
    }

    public static String getDescription(String url) {
        Document doc = load_url(url);
        String description = "";
        Elements metaTags = doc.select("meta[name=description]");
        if (metaTags != null && metaTags.size() > 0) {
            for (Element metaTag : metaTags) {
                description += metaTag.attr("content");
            }
        }
        return description;
    }

    public static String getKeywords(String url) {
        Document doc = load_url(url);
        String keywords = "";
        Elements metaTags = doc.select("meta[name=keywords]");
        if (metaTags != null && metaTags.size() > 0) {
            for (Element metaTag : metaTags) {
                keywords += metaTag.attr("content");
            }
        }
        return keywords;
    }

    public static String getBody(String url) {
        Document doc = load_url(url);
        if (doc.body() != null) {
            return doc.body().text();
        }
        return "";
    }

    public static String getheaders(String url) {
        Document doc = load_url(url);
        StringBuilder text = new StringBuilder();
        Elements elements = doc.select("h1,h2,h3,h4,div[id*=title],div[class*=title],span[id*=title],span[class*=title]");
        if (elements != null) {
            for (Element element : elements) {
                text.append(element.text());
                text.append(lineSeparator);
            }
        }
        return text.toString();
    }

    public static String getImportantBody(String url) {
        Document doc = load_url(url);
        StringBuilder text = new StringBuilder();
        Elements elements = doc.select("b,strong,em");
        if (elements != null) {
            for (Element element : elements) {
                text.append(element.text());
                text.append(lineSeparator);
            }
        }
        return text.toString();
    }

    public static DocumentEntity getDocumnet(String url) {
        Document document = load_url(url);
        return new DocumentEntity()
                .setUrl(url)
                .setTitle(getTitle(document))
                .setDescription(getDescription(document))
                .setBody(getBody(document));
    }

    public static void main(String[] args) {
        String url = "http://www.ics.uci.edu/~dvk/pub/SIGMODR04_dvk.html";

//			document doc = new document(url);
//			String a = doc.description;
//			System.out.println(a);
    }

}
