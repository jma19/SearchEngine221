package com.uci.utils;


import com.google.common.base.Strings;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by junm5 on 2/22/17.
 */
public class UrlChecker {
    // Filters, not to include page that has any one of these extensions
    private final static Pattern FILTERS = Pattern
            .compile(".*\\.(css|js|gif|jpe?g|png|mp3|zip|gz"
                    + "|bmp|ico|PNG|tiff?|mid|mp2|mp4|wav|avi"
                    + "|mov|mpeg|ram|aaf|asf|flv|m4v|mkv|ogg"
                    + "|ogv|pdf|ps|eps|tex|ppt|pptx|doc|docx"
                    + "|xls|xlsx|names|data|xaml|pict|rif|dat"
                    + "|exe|bz2|tar|msi|bin|7z|psd|dmg|iso|epub"
                    + "|dll|cnf|tgz|sha1|thmx|mso|arff|rtf|jar"
                    + "|csv|rm|smil|wm?v|swf|wma|au|aiff|flac"
                    + "|3gp|amr|au|vox|rar|aac|ace|alz|apk|arc"
                    + "|arj|lzip|lha|txt|java|javac|cc|h|pfm|" +
                    "|war|au|apk|db|z|java|c|py|lif|pov|bib|shar" +
                    "|txt|hs|lif|pl|jpg|r|out|.ss|pde|json|fasta|ppsx|cpp|cp)" + "(\\?.*)?$");


    // skip URLs containing certain characters as probable queries, etc.
    private final static Pattern QFILTERS = Pattern.compile(".*[\\?@=].*");

    public static boolean isValid(String url) {
        url = "http://" + url;

        if (Strings.isNullOrEmpty(url)) {
            return false;
        }
//        if (!HttpUtils.isValid(url)) {
//            return false;
//        }
        if (url.contains("?")) {
            return false;
        }
        if (containDuplictePath(url)) {
            return false;
        }
        return !FILTERS.matcher(url).matches()
                && !QFILTERS.matcher(url).matches();
    }

    private static boolean containDuplictePath(String url) {
        String[] split = url.split("/");
        Set<String> set = new HashSet<>();
        for (String token : split) {
            if (!token.isEmpty()) {
                if (!set.add(token)) {
                    return true;
                }
            }
        }
        return false;
    }
}
