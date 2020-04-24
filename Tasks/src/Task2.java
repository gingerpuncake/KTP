import java.util.Arrays;

public class Task2 {

    // 1 программа увечиливает количество каждого символа в n раз

    public static String repeat(String word, int n){
        String newWord = " ";
        for (int i = 0; i< word.length() ; i++){
            for (int j = 0; j<n; j++){
                newWord+= word.charAt(i);
            }
        }
        return newWord;
    }

    // 2 находит разницу между минимум и максимумом массива
    // минимуму присваивам максимально возможное число
    // максимуму - минимально возможное
    // находим минимум и максимум введенного массива
    // и возвращаем разницу между этими числами

    public static int differenceMaxMin(int [] mass){
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int value : mass) {
            min = Math.min(min, value);
            max = Math.max(max, value);
        }
        return max - min;
    }

    // 3 проверяет, является ли среднее значение всех элементов целым числом
    // суммируем все элементы массива
    // если среднее арифметическое целое число - возвращаем true
    // если нет - false

    public static boolean isAvgWhole(int mass[]){
        double sum=0;

        for (int value : mass) {
            sum += value;
        }
        if ((sum%mass.length)%100<1)
            return true;
        else
            return false;

    }

    // 4 возвращает массив
    // в котором каждое целое число - сумма самого себя
    // и всех предыдущих чисел

    public static int[] cumulativeSum( int [] mass){

        for (int i = 1; i < mass.length; i++){
            mass[i] = mass[i] + mass[i-1];
        }
        return mass;
    }

    //5 считает число десятичных знаков
    // если в числе есть точка
    // возвращаем длинну числа - порядковый номер точки - 1
    // если точки нет - возвращаем 0

    public static int getDecimalPlaces(String number){
        if (number.indexOf('.')!=0){
            return number.length()-number.indexOf('.')-1;
        }
        else
            return 0;
    }

    //6 возвращается n-ое число Фибоначчи
    // если просим вывести 0 или 1 число Фибоначчи,
    // то возвращаем 1

    public static int Fibonacci (int n){
        switch (n) {
            case 0:
            case 1:
                return 1;
            default:
                return Fibonacci(n - 2) + Fibonacci(n - 1);
        }
    }

    // 7 проверяет , првильно ли введен индекс
    // если в введенном индексе есть буквы, пробелы или не 5 символов
    // то счетчик принимает не нулевое значение
    // в противном случае выводится true

    public static boolean isValid(String index){
        int buf = 0;
        for (int i = 0; i < index.length(); i++) {
            if (!Character.isDigit(index.charAt(i)))
                buf++;
            else if (index.charAt(i) == ' ')
                buf++;
            else if (index.length() != 5)
                buf++;
        }
        return buf == 0;
    }

    //8 странная пара
    // если первый символ первого слова и последний второго совпадают
    // и первый элемент второго слова совпадает с последним элементом первого
    // то вернется true

    public static boolean isStrangePair (String word1, String word2){
        return word1.charAt(0) == word2.charAt(word2.length() - 1) &&
                word2.charAt(0) == word2.charAt(word1.length() - 1);
    }

    //9 проверяем является ли данный суффикс или префикс частью введенного слова
    //убираем из префикса/суффикса -
    // проверяем начинается/заканчивается ли введенное слово на суффикс/префикс

    public static boolean isPrefix(String word, String pref){
        pref =  pref.substring(0,  pref.length()-1);
        //System.out.println(pref);
        return word.startsWith(pref);
    }
    public static boolean isSuffix(String word, String suf){
        suf = suf.substring(1);
        //System.out.println(suf);
        return word.endsWith(suf);
    }

    // 10 Создайте функцию, которая принимает число (шаг) в качестве аргумента
    // и возвращает количество полей на этом шаге последовательности.
    // если шаг = 0 , возвращаем 0
    // создаем цикл, "делающий" шаги
    // если шаг четный, то к счетчику прибавляем 3
    // если шаг не четный, то из счетчика вычитается 1

    public static int boxSeq(int shag){
        if (shag == 0) return 0;
        int count = 0;
        for (int i = 0; i < shag; i++) {
            if (i % 2 != 0) count--;
            else count += 3;
        }
        return count;
    }

    public static void main(String[] args) {
        // Тест 1 - вернет  hhheeellllllooo
        System.out.println(repeat("hello", 3));
        // Тест 2 - вернет 82
        System.out.println(differenceMaxMin(new int[]{10, 4, 1, 4, -10, -50, 32, 21}));
        // Тест 3 - вернет false
        System.out.println(isAvgWhole(new int[]{1, 2 , 3 , 4}));
        // Тест 4 - вернет [3, 6, 4, 412, 415, 418]
        System.out.println(Arrays.toString(cumulativeSum(new int[]{3, 3, -2, 408, 3, 3})));
        // Тест 5 - вернет 2
        System.out.println(getDecimalPlaces("43.20"));
        // Тест 6 - вернет 21
        System.out.println(Fibonacci(7));
        // Тест 7 - вернет true
        System.out.println(isValid("15006"));
        // Тест 8 - вернет false
        System.out.println(isStrangePair("bush", "hubris") );
        // Тест 9 - вернет true и true
        System.out.println(isPrefix("automation", "auto-"));
        System.out.println(isSuffix("arachnophobia", "-phobia"));
        // Тест 10 - вернет 5
        System.out.println(boxSeq(3));

    }



}
