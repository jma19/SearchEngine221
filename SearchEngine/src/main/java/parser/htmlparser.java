import java.io.*;
import java.util.*;
import java.net.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class htmlparser {
	private final static String lineSeparator = System.getProperty("line.separator");
	private Document parseddoc;
	private String url;
	private String html;
	private boolean hasloaded;

	public htmlparser(String url){
		this.url = url;
		this.hasloaded = false;
	}
	private void load(){
		if(this.hasloaded) return;
		this.hasloaded = true;
		try{
			this.parseddoc = Jsoup.connect(url).get();
			this.html = parseddoc.html();
			Elements meta = this.parseddoc.select("html head meta");
			if(meta.attr("http-equiv").contains("REFRESH") || meta.attr("http-equiv").contains("refresh")){
				String content = meta.attr("content");
				if(content.contains("=")) {
					String[] parts = content.split("=");
					if(parts.length > 1) {
						String redirecturl = parts[1].trim();
						String mergedurl = combineUrls(url, redirecturl);
						if(mergedurl != null){
							this.parseddoc = Jsoup.connect(mergedurl.toString()).get();
							this.html = parseddoc.html();
						}
					}
				}
			}
		}
		catch (Exception e){
			System.out.println("Error on URL" + url);
			e.printStackTrace();
		}
	}
	public String getUrl() {
		return this.url;
	}
	public String getHtml(){
		this.load();
		return this.html;
	}
	public String getTitle(){
		this.load();
		return this.parseddoc.title();
	}
	public String getDescription(){
		this.load();
		String description = "";
		Elements metaTags = this.parseddoc.select("meta[name=description]");
		if(metaTags != null && metaTags.size() > 0) {
			for(Element metaTag : metaTags){
				description += metaTag.attr("content");
			}
		}
		return description;
	}
	public String getBody() {
		this.load();
		if(this.parseddoc.body() != null){
			return this.parseddoc.body().text();
		}
		return "";
	}
	public String getheaders(){
		this.load();
		StringBuilder text = new StringBuilder();
		Elements elements = this.parseddoc.select("h1,h2,h3,h4,div[id*=title],div[class*=title],span[id*=title],span[class*=title]");
		if(elements != null) {
			for(Element element: elements) {
					text.append(element.text());
					text.append(lineSeparator);
			}
		}
		return text.toString();
	}
	public String getImportantBody() {
		this.load();
		StringBuilder text = new StringBuilder();
		Elements elements = this.parseddoc.select("b,strong,em");
		if(elements != null){
			for(Element element : elements) {
				text.append(element.text());
				text.append(lineSeparator);
			}
		}
		return text.toString();
	}
	public String getAllText(){
		return getTitle() + lineSeparator + getDescription() + lineSeparator + getBody();
	}
	public HashMap<String, String> getOutgoingLinks() {
		this.load();
		HashMap<String,String> outgoingLinks = new HashMap<String,String>();
		Elements links = this.parseddoc.select("a");
		if(links != null) {
			for(Element link: links){
				String href = link.attr("href");
				if(href.contains("javascript:") || href.contains("mailto:")){
					continue;
				}
				String absoluteHref = combineUrls(this.url,href);
				if(absoluteHref == null)
					continue;
				if(!absoluteHref.contains("ics.uci.edu"))
					continue;
				String currenttext  = outgoingLinks.get(absoluteHref);
				if(currenttext == null){
					currenttext = "";
				}
				currenttext += " " + link.text();
				outgoingLinks.put(absoluteHref, currenttext.trim());

			}
		}
		return outgoingLinks;
	}
	private String combineUrls(String url, String relativeurl){
		if(relativeurl.contains("http"))
			return relativeurl;
		try {
			URI baseuri = new URI(url);
			return baseuri.resolve(new URI(relativeurl)).toString();
		}
		catch (URISyntaxException e){
			System.out.println("Error on url: " + url);
			e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args){
		String url = "http://www.ics.uci.edu/~dvk/pub/SIGMODR04_dvk.html";
		htmlparser p = new htmlparser(url);
		String a = p.getAllText();
		System.out.println(a);
	}
}
