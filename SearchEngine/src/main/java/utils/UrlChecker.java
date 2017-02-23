package utils;


import com.google.common.base.Strings;

/**
 * Created by junm5 on 2/22/17.
 */
public class UrlChecker {

    public static boolean isValid(String url) {
        if (Strings.isNullOrEmpty(url)) {
            return false;
        }

//        parsed = urlparse(url)
//
//        path = parsed.path
//        pathSplit1 = path.split('.php')
//        if len(pathSplit1) > 2:
//        return False

//        if parsed.query != '' or "../" in url or parsed.scheme not in set(["http", "https"]) \
//        or contain_duplicate(path):
//        return False
//
//    # check trap
//        for trap in TRAP_POOL:
//        if trap in url:
//        return False
//
//        parsed = urlparse(url)
//        if parsed.scheme not in set(["http", "https"]):
//        return False
//        try:
////        return ".ics.uci.edu" in parsed.hostname \
//        String reg = ".*\.(css|js|bmp|gif|jpe?g|ico|png|tiff?|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|mkv|ogg|ogv|pdf" +
//                "|ps|eps|tex|ppt|pptx|doc|docx|xls|xlsx|names|data|dat|exe|bz2|tar|msi|bin|7z|psd|dmg|iso|epub|dll|cnf|tgz|sha1" \
//        +"|thmx|mso|arff|rtf|jar|csv"
//                + "|rm|smil|wmv|swf|wma|zip|rar|gz"
//        "|war|au|apk|db|z|java|c|py|lif|pov|bib|shar|txt|hs|lif|pl|jpg|r|out|.ss|pde|json|fasta|ppsx )$";
//
////
//        except TypeError:
//        print ("TypeError for ", parsed)
        return true;
    }
}
