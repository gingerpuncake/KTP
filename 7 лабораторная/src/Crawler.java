import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;
public class Crawler {
    public static void main(String[] args) throws IOException {

        String[] arg = new String[] {"http://google.ru//","1","1"};
        // переменные для текущей глубины и запрошенного количества потоков
        int depth = 0;
        int numThreads = 0;
        // проверяем, правильное ли количество параметров было введено
        try {
            depth = Integer.parseInt(arg[1]);
            numThreads = Integer.parseInt(arg[2]);
        }
        catch (NumberFormatException nfe) {
            // проверяем являются ли глубина и количество потоков цифрами
            System.out.println("usage: java Crawler <URL> <depth> "
                    + "<number of crawler threads>");
            System.exit(1);
        }
        // создаем глубина URL-пары для представления веб-сайта
        URLDepthPair currentDepthPair = new URLDepthPair(arg[0], 0);
        // создаем URL пул и добавляем введенный пользователем веб-сайт
        URLPool pool = new URLPool(depth);
        pool.put(currentDepthPair);
        // поле для начального количества потоков
        int initialActiveThreads = Thread.activeCount();
        /* пока количество ожидающих потоки не равно их запрошенному числу и
        если количество всех потоков меньше их запрошенного количества, то
        создаём больше потоков и запускаем их на CrawlerTask. Иначе ждём. */
        while (pool.getWaitThreads() != numThreads) {
            if (Thread.activeCount() - initialActiveThreads < numThreads) {
                CrawlerTask crawler = new CrawlerTask(pool);
                new Thread(crawler).start();
            }
            else {
                try {
                    Thread.sleep(500); // 0,5 секунды
                }
                // ловим исключение для прерывания потока
                catch (InterruptedException ie) {
                    System.out.println("Caught unexpected InterruptedException,"
                            + " ignoring...");
                }
            }
        }
        // пока все потоки находятся в ожидании, печатаем обработанные URL
        Iterator<URLDepthPair> iter = pool.processedURLs.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
        // выход
        System.exit(0);
    }
    public static LinkedList<String> getAllLinks(URLDepthPair myDepthPair) throws IOException {
        // создаем связанный список LinkedList<String>, в котором будут храниться ссылки, что мы находим
        LinkedList<String> URLs = new LinkedList<String>();
        // Создаём новый сокет
        Socket sock;
        // Создаем новый сокет из строки String, содержащей имя хоста, и из номера порта, равного 80 (http)
        try {
            sock = new Socket(myDepthPair.getWebHost(), 80);
        }
        // ловим исключения неизвестного хоста и возвращаем пустой список
        catch (UnknownHostException e) {
            System.err.println("UnknownHostException: " + e.getMessage());
            return URLs;
        }
        // ловим исключения ввода-вывода и возвращаем пустой список
        catch (IOException ex) {
            //System.err.println("IOException: " + ex.getMessage());
            return URLs;
        }
        try {
            // устанавливаем таймаут сокета на 3 секунды
            sock.setSoTimeout(3000);
        }
        // ловим исключения сокета и возвращаем пустой список
        catch (SocketException exc) {
            System.err.println("SocketException: " + exc.getMessage());
            return URLs;
        }
        // поля для хранения пути к файлу и хоста
        String docPath = myDepthPair.getDocPath();
        String webHost = myDepthPair.getWebHost();
        // создаем поток вывода
        OutputStream outStream;
        // возвращаем OutputStream связанный с сокетом используемый для передачи данных
        try {
            outStream = sock.getOutputStream();
        }
        // ловим исключения ввода-вывода и возвращаем пустой список
        catch (IOException exce) {
            //System.err.println("IOException: " + exce.getMessage());
            return URLs;
        }
        // создаем PrintWriter, true означает, что PrintWriter будет сбрасываться после каждого вывода
        PrintWriter myWriter = new PrintWriter(outStream, true);
        if (docPath.length() == 0) {
            // Отправляем запрос на сервер
            myWriter.println("GET / HTTP/1.1");
            myWriter.println("Host: " + webHost);
            myWriter.println("Connection: close");
            myWriter.println();
        }
        else {
            myWriter.println("GET " + docPath + " HTTP/1.1");
            myWriter.println("Host: " + webHost);
            myWriter.println("Connection: close");
            myWriter.println();
        }
        // создаем поток ввода
        InputStream inStream;
        // возвращает InputStream связанный с объектом Socket используемый для приема данных
        try {
            inStream = sock.getInputStream();
        }
        // ловим исключения ввода-вывода и возвращаем пустой список
        catch (IOException excep) {
            //System.err.println("IOException: " + excep.getMessage());
            return URLs;
        }
        // Создаем новый InputStreamReader и BufferedReader для чтения строк с сервера
        InputStreamReader inStreamReader = new InputStreamReader(inStream);
        BufferedReader BuffReader = new BufferedReader(inStreamReader);
        // проверям код ответа сервера
        int serverCode = 0;
        String lineCode;
        try {
            lineCode = BuffReader.readLine();
        }
        // ловим исключения ввода-вывода и возвращаем пустой список
        catch (IOException except) {
            //System.err.println("IOException: " + except.getMessage());
            return URLs;
        }
        // Если сервер вернул пустой ответ, то заканчиваем обработку
        if (lineCode == null) {
            System.out.println("Ошибка: сайт \"" + myDepthPair.getURL() +
                    "\" вернул пустой ответ");
            // возвращаем пустой список ссылок
            return URLs;
        }
        // создаем паттерн для кодов html: 2xx, 3xx, 4xx
        Pattern patternCode = Pattern.compile("(2|3|4)[0-9]{2}");
        Matcher matcherCode = patternCode.matcher(lineCode);
        // поиск кода html: 2xx, 3xx, 4xx
        while (matcherCode.find()) {
            serverCode = Integer.valueOf(lineCode.substring(matcherCode.start(), matcherCode.end() - 2));
        }
        // Обработка для кодов html, равных 2xx
        if (serverCode == 2) {
            while (true) {
                String line;
                try {
                    line = BuffReader.readLine();
                }
                // ловим исключения ввода-вывода и возвращаем пустой список
                catch (IOException except) {
                    //System.err.println("IOException: " + except.getMessage());
                    return URLs;
                }
                // прекращаем чтения документа
                if (line == null) {
                    break;
                }
                // создаем паттерн для поиска URL
                Pattern patternURL = Pattern.compile(
                        "[\"]"// перед ссылкой должно быть кавычка
                                + "[https?://]{7,8}"// может быть http://, а может быть https://
                                + "([w]{3})?" // www может быть, а может не быть
                                + "[\\w\\.\\-]+" // хост сайта без домена 1-ого уровня
                                + "\\." // точка перед доменом 1-ого уровня
                                + "[A-Za-z]{2,6}" // домен 1-ого уровня
                                + "[\\w\\.-/]*" // путь к странице
                                + "[\"]"); // после ссылки должно быть кавычка
                Matcher matcherURL = patternURL.matcher(line);
                // поиск URL в строке с помощью паттерна
                while (matcherURL.find()) {
                    String newLink = line.substring(matcherURL.start() + 1,
                            matcherURL.end() - 1);
                    // добавляем ссылки в список URLs
                    URLs.add(newLink);
                }
            }
            sock.close();
            // возвращаем все ссылки на рассматриваемом сайте.
            return URLs;
        }
        // обработка для кодов html, равных 3xx
        if (serverCode == 3) {
            // поле для исправленного URL
            String newURL = "";
            String tempLine;
            while (true) {
                try {
                    tempLine = BuffReader.readLine();
                }
                // ловим исключения ввода-вывода и возвращаем пустой список
                catch (IOException except) {
                    //System.err.println("IOException: " + except.getMessage());
                    return URLs;
                }
                // прекращаем чтения документа
                if (tempLine == null) {
                    break;
                }
                // паттерн для поиска URL для перенаправления
                Pattern patternNewURL = Pattern.compile("(Location: ){1}[\\S]+");
                Matcher matcherNewURL = patternNewURL.matcher(tempLine);
                while (matcherNewURL.find()) {
                    newURL = tempLine.substring(matcherNewURL.start() + 10,
                            matcherNewURL.end());
                }
            }
            if (newURL.equals(myDepthPair.getURL())) {
                /* System.out.println("Ошибка: сайт \"" + myDepthPair.getURL() +
                "\" перенаправляет на самого себя" + " (код ответа HTML 3xx)"); */
                sock.close();
                // возвращаем пустой список ссылок
                return URLs;
            }
            URLDepthPair newDepthPair;
            newDepthPair = new URLDepthPair(newURL, myDepthPair.getDepth());
            // вызываем метод getAllLinks с исправленным URL
            return getAllLinks(newDepthPair);
        }
        // обработка для кодов html, равных 4xx
        else {
            System.out.println("Ошибка: сайт \"" + myDepthPair.getURL() +
                    "\" недоступен (код ответа HTML 4xx)");
            sock.close();
            // возвращаем пустой список ссылок
            return URLs;
        }
    }
}