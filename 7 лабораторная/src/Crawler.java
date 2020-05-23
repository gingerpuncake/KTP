import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;
public class Crawler {

    public static void main(String[] args) throws IOException {

        String[] arg = new String[] {"https://htmlbook.ru//","1"};
        // глубина
        int depth = 0;
        // проверяем, сколько параментров введено
        if (arg.length != 2) {
            // если не 2, выводится ошибка, работа программы останавливается
            System.out.println("usage: java Crawler <URL> <depth>");
            System.exit(1);
        }
        // если все введено верно, то
        else {
            try {
                // устанавливаем глубину
                depth = Integer.parseInt(arg[1]);
            }
            // проверяем является ли глубина цифрой
            catch (NumberFormatException nfe) {
                System.out.println("usage: java Crawler <URL> <depth>");
                System.exit(1);
            }
        }
        // список для представления ожидающих URL-адресов
        LinkedList<URLDepthPair> pendingURLs = new LinkedList<URLDepthPair>();
        //  связный список для представления обработанных URL
        LinkedList<URLDepthPair> processedURLs = new LinkedList<URLDepthPair>();
        // глубина URL-пары для представления веб-сайта
        URLDepthPair currentDepthPair = new URLDepthPair(arg[0], 0);
        // добавляем введенный веб-сайт в ожидающие URL-адреса
        pendingURLs.add(currentDepthPair);
        // добавляем текущий веб-сайт
        ArrayList<String> seenURLs = new ArrayList<String>();
        seenURLs.add(currentDepthPair.getURL());

        // пока URL-адреса не станут пустыми, получаем все ссылки от каждого посещенного сайта
        while (pendingURLs.size() != 0) {
            // добавляем URL в список обработанных и сохранем глубину
            URLDepthPair depthPair = pendingURLs.pop();
            processedURLs.add(depthPair);
            int myDepth = depthPair.getDepth();
            // получаем все ссылки с сайта и сохраняем их в новом связном списке
            LinkedList<String> linksList;
            linksList = Crawler.getAllLinks(depthPair);
            // добавляем ссылки, которые раньше не были видны, если мы не достигли максимальной глубины
            if (myDepth < depth) {
                // перебирать ссылки с сайта
                for (String newURL : linksList) {
                    // продолжаем, если мы уже видели ссылку
                    if (seenURLs.contains(newURL)) {
                        continue;
                    }
                    // cоздаем новую пару URL-глубина, если ссылка не встречалась ранее, и добвляем в списки
                    else {
                        URLDepthPair newDepthPair = new URLDepthPair(newURL, myDepth + 1);
                        pendingURLs.add(newDepthPair);
                        seenURLs.add(newURL);
                    }
                }
            }
        }
        // выводим все обработанные URL с глубиной
        for (URLDepthPair processedURL : processedURLs) {
            System.out.println(processedURL);
        }
    }
    private static LinkedList<String> getAllLinks(URLDepthPair myDepthPair) throws IOException {
        // создаем связанный список LinkedList<String>, в котором будут храниться ссылки, что мы находим
        LinkedList<String> URLs = new LinkedList<String>();
        // Новый сокет
        Socket sock;
        // Новый сокет содержащей имя хоста, и из номера порта, равного 80 (http)
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
                    return URLs;
                }

                // прекращаем чтения документа
                if (line == null) {
                    break;
                }
                // создаем паттерн для поиска URL
                Pattern patternURL = Pattern.compile(
                        "[\"]"// перед ссылкой должно быть кавычка
                                + "[https?://]{7,8}" + "([w]{3})?" + "[\\w\\.\\-]+" + "\\." + "[A-Za-z]{2,6}" + "[\\w\\.-/]*"
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
                    return URLs;
                }
                // прекращаем чтения документа
                if (tempLine == null) {
                    break;
                }

                Pattern patternNewURL = Pattern.compile("(Location: ){1}[\\S]+");
                Matcher matcherNewURL = patternNewURL.matcher(tempLine);
                while (matcherNewURL.find()) {
                    newURL = tempLine.substring(matcherNewURL.start() + 10,
                            matcherNewURL.end());
                }
            }
            if (newURL.equals(myDepthPair.getURL())) {
                sock.close();
                // возвращаем пустой список ссылок
                return URLs;
            }
            URLDepthPair newDepthPair;
            newDepthPair = new URLDepthPair(newURL, myDepthPair.getDepth());
            // вызываем метод getAllLinks с исправленным URL
            return getAllLinks(newDepthPair);
        }
        return URLs;
    }
}