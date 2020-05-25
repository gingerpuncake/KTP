import java.util.*;
public class URLPool {
    // не просмотренные пары <URL, глубина>
    private final LinkedList<URLDepthPair> pendingURLs;
    // просмотренные пары <URL, глубина>
    public LinkedList<URLDepthPair> processedURLs;
    // просмотренные URL=
    private final ArrayList<String> seenURLs = new ArrayList<>();
    // количество потоков, которые ожидают обработки
    public int waitingThreads;
    int maxDepth;
    // конструктор для инициализации waitingThreads, processedURLs и pendingURLs
    public URLPool(int maxDepthPair) {
        maxDepth = maxDepthPair;
        waitingThreads = 0;
        pendingURLs = new LinkedList<>();
        processedURLs = new LinkedList<>();
    }
    // метод для доступа к waitingThreads
    public synchronized int getWaitThreads() {
        return waitingThreads;
    }
    // метод для получения размера pendingURLs
    public synchronized int size() {
        return pendingURLs.size();
    }
    // метод для добавление новой пары <URL, глубина>
    public synchronized void put(URLDepthPair depthPair) {
        // если был вызван put и есть потоки, находящиеся в ожидании, то надо вызвать эти потоки и уменьшить их счётчик
        if (waitingThreads != 0) {
            --waitingThreads;
            this.notify();
        }
        if (!seenURLs.contains(depthPair.getURL()) &
                !pendingURLs.contains(depthPair)) {
            if (depthPair.getDepth() < maxDepth) {
                pendingURLs.add(depthPair);
            }
            else {
                processedURLs.add(depthPair);
                seenURLs.add(depthPair.getURL());
            }
        }
    }
    // метод для получения следующей пары из пула
    public synchronized URLDepthPair get() {
        URLDepthPair myDepthPair;
        // пока пул пуст, ждем
        while (pendingURLs.isEmpty()) {
            waitingThreads++;
            try {
                this.wait();
            }
            // ловим исключение для прерывания потока
            catch (InterruptedException e) {
                System.err.println("MalformedURLException: " + e.getMessage());
                return null;
            }
        }
        // удаляем первую пару, добавляем к просмотренным и обработанным URL, и возвращаем
        myDepthPair = pendingURLs.pop();

        while (seenURLs.contains(myDepthPair.getURL())) {

            myDepthPair = pendingURLs.pop();
        }

        processedURLs.add(myDepthPair);
        seenURLs.add(myDepthPair.getURL());

        return myDepthPair;
    }
}