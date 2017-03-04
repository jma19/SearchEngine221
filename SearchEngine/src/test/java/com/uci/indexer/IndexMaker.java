//package com.uci.indexer;
//
//import com.uci.utils.SysPathUtil;
//
//import java.io.BufferedReader;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class IndexMaker {
//	public static void makeIndex(String fileName){
//		try {
//			BufferedReader br=new BufferedReader(new FileReader(fileName));
//			Pattern P = Pattern.compile("(<<<)(.*)(>>>)");
//			String tempLine = new String();
//		    String url = new String();
//		    List<String> wordsInPage = new LinkedList<String>();
//		    int pageNumber = 0;
//		    int wordPosition = 0;
//		    Map<String, Term> termMap = new HashMap<String, Term>();
//		    try {
//				while((tempLine=br.readLine())!=null){
//					Matcher M = P.matcher(tempLine);
//					if(M.matches()){
//						if(!wordsInPage.isEmpty()){
//							//new Document(pageNumber, url, wordsInPage);
//							wordsInPage = new LinkedList<String>();
//						}
//						url=M.group(2);
//						pageNumber++;
//						wordPosition = 0;
//
//					}
//					else{
//						System.out.println(tempLine);
////						wordsInPage.addAll(tokenLine);
////						for(String word : tokenLine){
////							System.out.println(word);
////
////							wordPosition++;
////						}
//					}
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//	}
//	public static void main(String[] agrs){
//		String path = SysPathUtil.getSysPath() + "/SearchEngine/WEBPAGES_RAW/";
//		makeIndex(path + "0/2");
//		return;
//	}
//}
