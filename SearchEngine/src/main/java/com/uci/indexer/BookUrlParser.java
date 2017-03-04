//package com.uci.indexer;
//
//import com.uci.io.MyFileReader;
//import com.uci.io.MyFileWriter;
//import com.uci.mode.URLPath;
//import com.uci.utils.JsonUtils;
//import com.uci.utils.UrlChecker;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * Created by junm5 on 2/23/17.
// */
//public class BookUrlParser {
//
//    public List<URLPath> loadUrlPathsFrom(String filePath) {
//        MyFileReader myFileReader = null;
//        try {
//            myFileReader = new MyFileReader(filePath);
//            List<URLPath> urlPaths = new ArrayList<>();
//            String line;
//            while ((line = myFileReader.readLines()) != null) {
//                if (!line.isEmpty()) {
//                    String[] split = line.split("\t");
//                    urlPaths.add(new URLPath(split[0].trim(), split[1].trim()));
//                }
//            }
//            return urlPaths.stream()
//                    .filter(urlPath -> UrlChecker.isValid(urlPath.getUrl()))
//                    .map(urlPath -> urlPath.setUrl("http://" + urlPath.getUrl()))
//                    .collect(Collectors.toList());
//        } finally {
//            myFileReader.close();
//        }
//    }
//
//    public void saveToJosn(List<URLPath> urlPaths, String validUrl) {
//        System.out.println(urlPaths.size());
//        MyFileWriter.createFile(validUrl);
//        MyFileWriter myFileWriter = null;
//        try {
//            myFileWriter = new MyFileWriter(validUrl, true);
//            String res = JsonUtils.toJson(urlPaths);
//            myFileWriter.writeLine(res);
//            myFileWriter.flush();
//        } finally {
//            myFileWriter.close();
//        }
//    }
//}
