package com.company;
import java.util.Scanner;
import java.lang.String;

public class Main {

    public static void main(String[] args){
        System.out.println("Введите слово для проверки:");
        //считываем слово
        Scanner scan = new Scanner(System.in);
        //вносим его в переменную s
        String s=scan.next();
        // передаем это значение в класс
        isPalindrome(s);
    }
    public static String reverseString(String s){
        //созданем пустую строку, в которую позже занесем перевернутую введенную строку
        String r = "";
        // переворачиваем строку
        for (int i = s.length() - 1; i >= 0; --i)
            //записываем символы в r
            r += s.charAt(i);
        return r;

    }
    public static Boolean isPalindrome(String s) {
        // проверяем, совпадает ли введенное слово с перевертышем
        if(s.equals(reverseString(s))){
            System.out.println("Палиндром");
        }
        else {
            System.out.println("Не палиндром");
        }
        return s.equals(reverseString(s));

    }

}