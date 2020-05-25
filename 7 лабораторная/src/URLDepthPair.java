import java.net.*;
public class URLDepthPair {
    // URL
    private final String currentURL;
    // глубина
    private final int currentDepth;
    // конструктор, который устанавливает ввод для текущих URL и глубины
    public URLDepthPair(String URL, int depth) {
        currentURL = URL;
        currentDepth = depth;
    }
    // текущий URL
    public String getURL() {
        return currentURL;
    }
    // текущая глубину
    public int getDepth() {
        return currentDepth;
    }
    // тоже самое , но в виде строки
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
            //System.err.println("MalformedURLException: " + e.getMessage());
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
            //System.err.println("MalformedURLException: " + e.getMessage());
            return null;
        }
    }
}