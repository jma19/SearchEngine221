package com.uci.utils;

import com.github.kevinsawicki.http.HttpRequest;

/**
 * Created by junm5 on 2/22/17.
 */
public class HttpUtils {
    /**
     * check whether a url can be accessed
     *
     * @param url
     * @return
     */
    public static boolean isValid(String url) {
        int code = HttpRequest.get(url).code();
        return code == 200;
    }
}
