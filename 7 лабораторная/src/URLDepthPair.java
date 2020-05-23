import java.net.*;
public class URLDepthPair {

    private final String currentURL;
    private final int currentDepth;

    // конструктор
    public URLDepthPair(String URL, int depth) {
        // текущий URL
        currentURL = URL;
        // глубина
        currentDepth = depth;
    }

    // метод, который возвращает текущий URL
    public String getURL() {
        return currentURL;
    }
    // метод, который возвращает текущую глубину
    public int getDepth() {
        return currentDepth;
    }

    // текущий URL и глубина, но в виде строки
    public String toString() {
        String stringDepth = Integer.toString(currentDepth);
        return stringDepth + '\t' + currentURL;
    }

    // метод, который возвращает путь документа текущего URL
    public String getDocPath() {
        try {
            URL url = new URL(currentURL);
            return url.getPath();
        }
        catch (MalformedURLException e) {
            return null;
        }
    }

    // метод, который возращает хост текущего URL
    public String getWebHost() {
        try {
            URL url = new URL(currentURL);
            return url.getHost();
        }
        catch (MalformedURLException e) {
            return null;
        }
    }
}