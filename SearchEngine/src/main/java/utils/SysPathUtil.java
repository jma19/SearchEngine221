package utils;

/**
 * Created by junm5 on 2/24/17.
 */
public class SysPathUtil {
    private static String sysPath = System.getProperty("user.dir");

    public static String getSysPath() {
        return sysPath;
    }
}
