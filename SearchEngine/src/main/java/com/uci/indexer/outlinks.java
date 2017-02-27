import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import java.io.*;
import java.utils.*;

public class outlinks{


public HashMap<String, String> getOutgoingLinks(String url) {
		Document doc = Htmlparser.load_url(url);
		HashMap<String, String> outgoingLinks = new HashMap<String, String>(); 
		Elements links = doc.select("a");
		if (links != null) {
			for (Element link : links) {
				String href = link.attr("href");
				if (href.contains("javascript:") || href.contains("mailto:") || href.contains("https"))
					continue;

				String absoluteHref = combineUrls(url, href);

				if (absoluteHref == null)
					continue;

				if (!absoluteHref.contains("ics.uci.edu") || absoluteHref.contains("https"))
					continue;

				String currentAnchorText = outgoingLinks.get(absoluteHref);
				if (currentAnchorText == null)
					currentAnchorText = "";

				currentAnchorText += " " + link.text();
				outgoingLinks.put(absoluteHref, currentAnchorText.trim());
			}
		}

		return outgoingLinks;
	}
private String combineUrls(String url, String relativeUrl) {
		if (relativeUrl.contains("http"))
			return relativeUrl;

		try {
			URI baseUri = new URI(url);
			return baseUri.resolve(new URI(relativeUrl)).toString();
		}
		catch (URISyntaxException e) {
			System.out.println("Error on URL: (" + url + ", " + relativeUrl + ")");
			e.printStackTrace();
		}

		return null;
	}


}
