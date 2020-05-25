import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.*;
public class CrawlerTask implements Runnable {
    // поле для заданной пары глубин
    public URLDepthPair depthPair;
    // поле для URL пула
    public URLPool pool;
    // конструктор для инициализации пула
    public CrawlerTask (URLPool newPool) {
        pool = newPool;
    }
    // метод для запуска задач в CrawlerTask
    public void run() {
        // Достаём из пула следующую пару
        depthPair = pool.get();
        int depth = depthPair.getDepth();
        // поиск всех ссылок на рассматриваемом сайте и сохранение их в linksList
        LinkedList<String> linksList = null;
        try {
            linksList = Crawler.getAllLinks(depthPair);
        }
        // ловим исключения ввода-вывода
        catch (IOException ex) {
            Logger.getLogger(CrawlerTask.class.getName()).log(Level.SEVERE,null, ex);
        }
        // перебираем ссылки с сайта
        for (int i = 0; i < linksList.size(); i++) {
            String newURL = linksList.get(i);
            // создаем новую пару для каждой ссылки и добавляем её в пул
            URLDepthPair newDepthPair = new URLDepthPair(newURL, depth + 1);
            pool.put(newDepthPair);
        }
    }
}