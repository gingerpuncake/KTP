import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;

public class Task6 {

    /*1. Число Белла - это количество способов, которыми массив из n элементов может
    быть разбит на непустые подмножества. Создайте функцию, которая принимает
    число n и возвращает соответствующее число Белла.*/

    public static int bell(int num) {
        int[][] mass = new int[num + 1][num + 1];
        int sum = 0;
        mass[0][0] = 1;
        mass[num][num] = 1;
        for (int n = 1; n <= num; n++) {
            for (int i = 1; i < num; i++) {
                mass[n][i] = mass[n - 1][i - 1] + i * mass[n - 1][i];
            }
        }
        for (int i = 0; i <= num; i++) sum += mass[num][i];
        return sum;
    }

    /* 2. В «поросячей латыни» (свинский латинский) есть два очень простых правила:
            – Если слово начинается с согласного, переместите первую букву (буквы) слова до
    гласного до конца слова и добавьте «ay» в конец.*/

    public static String translateWord(String word){
            if (word.matches("[aeiouy]")) {
                word += "yay";
            }
            else {
                String newWord = word.split("[aeiouy]")[0];
                word = word.replaceFirst(newWord,"")+newWord+"ay";
            }
        return word;
    }

    public static String translateSentence(String word){
        String vow = "aeiouyAEIOUY";
        String[] newword = word.substring(0, word.length()-1).split(" ");
        word = "";
        for (int i = 0 ; i < newword.length; i++) {
            for (int j = 0; j < vow.length(); j++) {
                if (vow.indexOf(newword[i].charAt(j)) != -1) {
                    word += newword[i] + "yay ";
                    break;
                }
                else {
                    String newWord = newword[i].split("[aeiyouAEIYOU]")[0];
                    word += newword[i].replaceFirst(newWord, "") + newWord + "ay ";
                    break;
                }
            }
        }
        return word + ".";
    }

    /* 3. Учитывая параметры RGB (A) CSS, определите, является ли формат принимаемых
    значений допустимым или нет. Создайте функцию, которая принимает строку
            (например, " rgb(0, 0, 0)") и возвращает true, если ее формат правильный, в
    противном случае возвращает false.*/

    public static boolean validColor (String rgb){
        int num = rgb.indexOf('(');
        if (rgb.contains(" "))
            return false;
        String [] newrgb = rgb.substring(rgb.indexOf('(')+1,rgb.indexOf(')')).split(",");
        double [] n = new double [num];
        for (int i = 0 ; i < num; i++) {
            n[i] = Double.parseDouble(newrgb[i]);
        }
        for (int i = 0 ; i < num; i++) {
            if (n[i] >= 0 && n[i] <= 255 && num == newrgb.length)
                return true;
            else
                return false;
        }
        return false;
    }

    /* 4. Создайте функцию, которая принимает URL (строку), удаляет дублирующиеся
    параметры запроса и параметры, указанные во втором аргументе (который будет
    необязательным массивом).*/

    // https://edabit.com?a=1&b=2&a=2

    public static String stripUrlParams (String url, String ... paramsToStrip) {
        String str = "";
        if (url.indexOf("?") == -1)
            return url;
        else {
            str = url.substring(url.indexOf("?") + 1);
            url = url.substring(0, url.indexOf("?") + 1);
        }
        char[] params = str.toCharArray();

        String print = "";
        for (char param : params) {
            if (Character.isLetter(param))
                if (!(print.contains(String.valueOf(param)))) {
                    if (paramsToStrip.length > 0) {
                        for (String arg : paramsToStrip) {
                            if (!(arg.contains(String.valueOf(param))))
                                print += str.substring(str.lastIndexOf(param), str.lastIndexOf(param) + 3) + "&";
                        }
                    }
                    else
                        print += str.substring(str.lastIndexOf(param), str.lastIndexOf(param) + 3) + "&";
                }
        }
        return url + print.substring(0, print.length()-1);
    }

    /* 5. Напишите функцию, которая извлекает три самых длинных слова из заголовка
    газеты и преобразует их в хэштеги. Если несколько слов одинаковой длины,
    найдите слово, которое встречается первым.*/

    public static String getHashTags(String str){
        String[] hashtags = new String[]{" ", " ", " "};
        String buf = "";
        for (int i = 0 ; i<str.length();i++){
            if (str.charAt(i) != ' ')
                buf+=str.charAt(i);
            else if (buf.length() > hashtags[0].length()) {
                hashtags[0] = buf;
                buf = "";
            }
            else if (buf.length() > hashtags[1].length()) {
                hashtags[1] = buf;
                buf = "";
            }
            else if (buf.length() > hashtags[2].length()) {
                hashtags[2] = buf;
                buf = "";
            }
            else
                buf = "";
        }
        for (int i = 0; i < hashtags.length; i++){
            hashtags[i] = "#" + hashtags[i].toLowerCase();
        }
        return Arrays.toString(hashtags);
    }
    
    /* 6.Создайте функцию, которая принимает число n и возвращает n-е число в
    последовательности Улама.*/

    public static int ulma (int n){
        int[] mas = new int[n];
        mas[0]=1;
        mas[1]=2;
        int len=2, next=3;
        while (next<Integer.MAX_VALUE && len<n){
            int count =0;
            for (int i=0;i<len;i++){
                for (int j=len-1; j>i; j--){
                    if (mas[i]+mas[j]==next && mas[i]!=mas[j])
                        count++;
                    else if (count > 1)
                        break;
                }
                if (count > 1)
                    break;
            }
            if (count == 1) {
                mas[len]=next;
                len++;
            }
            next++;
        }
        return mas[n-1];
    }
    /* 7. Напишите функцию, которая возвращает самую длинную неповторяющуюся
    подстроку для строкового ввода.*/

    public static String longestNonrepeatingSubstring(String str){
        String podstr = "";
        char [] mass = str.toCharArray();
        str="";
        for(int i =0 ; i<mass.length;i++){
            if (!str.contains(String.valueOf(mass[i])))
                str+=mass[i];
            else{
                if (str.length()>podstr.length())
                    podstr=str;
                str=""+mass[i];
            }
        }
        if (str.length()>podstr.length())
            podstr=str;
        return podstr;
    }

    /* 8. Создайте функцию, которая принимает арабское число и преобразует его в римское
    число. */
    public static String convertToRoman (int num){
        String roman = "";
        if (num < 1 || num > 3999)
            return "Слишком большое число :( ";
        while (num >= 1000) {
            roman += "M";
            num -= 1000;        }
        while (num >= 900) {
            roman += "CM";
            num -= 900;
        }
        while (num >= 500) {
            roman += "D";
            num -= 500;
        }
        while (num >= 400) {
            roman += "CD";
            num -= 400;
        }
        while (num >= 100) {
            roman += "C";
            num -= 100;
        }
        while (num >= 90) {
            roman += "XC";
            num -= 90;
        }
        while (num >= 50) {
            roman += "L";
            num -= 50;
        }
        while (num >= 40) {
            roman += "XL";
            num -= 40;
        }
        while (num >= 10) {
            roman += "X";
            num -= 10;
        }
        while (num >= 9) {
            roman += "IX";
            num -= 9;
        }
        while (num >= 5) {
            roman += "V";
            num -= 5;
        }
        while (num >= 4) {
            roman += "IV";
            num -= 4;
        }
        while (num >= 1) {
            roman += "I";
            num -= 1;
        }
        return roman;
    }

    /* 9. Создайте функцию, которая принимает строку и возвращает true или false в
    зависимости от того, является ли формула правильной или нет. */

    public static boolean formula(String formula){
        String[] mass = formula.split(" ");
        int ans = 0;
        int expectedResult = 0;
        if (!Character.isDigit(mass[0].charAt(0))) return false;
        else ans = Integer.parseInt(mass[0]);
        int i = 1;
        while (!mass[i].equals("=")) {
            if (mass[i].equals("+")){
                ans += Integer.parseInt(mass[i + 1]);
            }
            if (mass[i].equals("-")){
                ans -= Integer.parseInt(mass[i + 1]);
            }
            if (mass[i].equals("*")){
                ans *= Integer.parseInt(mass[i + 1]);
            }
            if (mass[i].equals("/")){
                ans /= Integer.parseInt(mass[i + 1]);
            }
            i += 2;
        }
        i = formula.indexOf('=');
        String check = formula.substring(i + 1, formula.length());
        if (check.contains("=")) return false;
        else expectedResult = Integer.parseInt(mass[mass.length - 1]);
        if (ans == expectedResult) return true;
        else return false;
    }

    /* 10. Число может не быть палиндромом, но его потомком может быть. Прямой потомок
    числа создается путем суммирования каждой пары соседних цифр, чтобы создать
    цифры следующего числа.*/

    public static boolean palindromedescendant(int num){
        boolean bul = false;
        int buf = 0;
        StringBuffer nuum =new StringBuffer(num);
        StringBuffer nuuum =new StringBuffer(num);
        String res = " ";
        if (nuum.length()%2!=0)
            return false;
        else{
            while (!bul){
                if(nuum != nuum.reverse()){
                    for(int i=0; i<nuum.length();i+=2){
                        int a = Integer.parseInt(String.valueOf(nuum.charAt(i)));
                        int b = Integer.parseInt(String.valueOf(nuum.charAt(i+1)));
                        nuuum.append(a+b);
                    }
                    res=nuuum.toString();
                }
                else
                    bul = true;
            }
        }
        return bul;
    }


    public static void main(String[] args){
        System.out.println("№ 1. " + bell(3));
        System.out.println("№ 2.1 " + translateWord("trebuchet"));
        System.out.println("№ 2.2 " + translateSentence("I like to eat honey waffles."));
        System.out.println("№ 3. " + validColor("rgba(0,0,0,0.123456789)"));
        System.out.println("№ 4. " + stripUrlParams("https://edabit.com?a=1&b=2&a=2"));
        System.out.println("№ 5. " + getHashTags(("Visualizing Science")));
        System.out.println("№ 6. " + ulma(206));
        System.out.println("№ 7. " + longestNonrepeatingSubstring("aaabccaaaa"));
        System.out.println("№ 8. " + convertToRoman(16));
        System.out.println("№ 9. " + formula("6 * 4 = 24"));
        System.out.println("№ 10. " + palindromedescendant(11211230));
    }
}
