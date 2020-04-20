package com.company;
import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        System.out.println("Введите число: ");
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        boolean bl = true;

        System.out.println("Простые числа: ");

        for (int i = 2; i <= n; i++) {
            for (int j = 2; j < i; j++) {
                if (i % j == 0) {
                    bl = false;
                    break;
                }
            }
            if (bl) System.out.print(i + " ");
            else bl = true;
        }
    }
}
