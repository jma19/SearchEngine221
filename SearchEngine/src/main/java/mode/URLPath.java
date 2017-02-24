package mode;

/**
 * Created by junm5 on 2/22/17.
 */
public class URLPath {
    private String path;
    private String url;

    public URLPath(){
        super();
    }

    public URLPath(String path, String url) {
        this.path = path;
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public URLPath setPath(String path) {
        this.path = path;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public URLPath setUrl(String url) {
        this.url = url;
        return this;
    }

    @Override
    public String toString() {
        return "URLPath{" +
                "path='" + path + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
