public class Task1 {

        // 1
        //  Два числа передаются в качестве параметров.
        //  Первый параметр, разделенный на второй параметр,
        //  будет иметь остаток, возможно, ноль. Верните это значение.

        public static int remainder (int a, int b){
            return a % b ;
        }

        //2
        //Напишите функцию, которая принимает основание и высоту треугольника
        // и возвращает его площадь.
        public static double triArea (int a, int h){
            return 0.5*a*h ;
        }

        //3
        // реализовать функцию, которая возвращает обшее количество ног всех животных

        public static int animal(int chickens, int cows, int pigs){
            return chickens*2 + cows* 4 + pigs*4 ;
        }

        //4
        //Создайте функцию,
        // которая принимает три аргумента (prob, prize, pay)
        // и возвращает true, если prob * prize > pay;
        // в противном случае возвращает false.

        public static boolean profitableGamble ( double prob, double prize, double pay){
            if (prob*prize>pay)
                return true;
            else
                return false;
        }

        //5
        //Напишите функцию, которая принимает 3 числа и возвращает,
        // что нужно сделать с a и b:
        // они должны быть сложены, вычитаны, умножены или разделены,
        // чтобы получить N. Если ни одна из операций не может дать N,
        // верните "none". 3 числа – это N, a и b.

        public static String operation(int N, int a, int b) {
            if (a+b == N)
                return "added";
            else if (a-b == N)
                return "subtracted";
            else if (a*b == N)
                return "multiply";
            else if (a/b == N)
                return "davide";
            else return "none";
        }

        // 6
        //Создайте функцию, которая возвращает значение ASCII переданного символа.

        public static int ctoa(char a) {
            return (int) a;
        }

        // 7
        //Напишите функцию,
        // которая берет последнее число из
        // последовательного списка чисел и возвращает
        // сумму всех чисел до него и включая его.

        public static int addUpTo(int n){
            int mass[] = new int[n];
            int sum = 0;
            //создаем массив
            for(int i = 0; n > i; i++){
                mass[i] = i+1;
                sum = sum + mass[i];
            }
            return sum;
        }

        // 8
        //Создайте функцию,
        // которая находит максимальное значение третьего ребра треугольника,
        // где длины сторон являются целыми числами.

        public static int nextEdge(int a, int b){
            return a + b -1;
        }

        // 9
        //Создайте функцию,
        // которая принимает массив чисел и возвращает сумму его кубов.

        public static int sumOfCubes(int [] mass){
            int sum = 0;
            for (int i = 0 ; i < mass.length ; i++){
                sum += Math.pow(mass[i], 3);
            }
            return sum;
        }

        //10
        // Создайте функцию,
        // которая будет для данного a, b, c выполнять следующие действия:
        // – Добавьте A к себе B раз.
        // – Проверьте, делится ли результат на C.

        public static boolean abcmath(int a, int b, int c){
            for (int i = 0; i<b ; i++){
                a=a+a;
            }
            if (a%c == 0)
                return true;
            else
                return false;
        }

        public static void main(String[] args) {
            //тест 1 - вернет 3
            System.out.println(remainder(3,4));
            // текст 2 - вернет 3
            System.out.println(triArea(3,2));
            // текст 3 - вернет 36
            System.out.println(animal(2,3,5 ));
            // текст 4 - вернет true
            System.out.println(profitableGamble(0.2,50, 9));
            // текст 5 - вернет subtracted
            System.out.println(operation(24,26,2));
            // текст 6 - вернет 65
            System.out.println(ctoa('A'));
            // текст 7 - вернет 55
            System.out.println(addUpTo(10));
            // текст 8 - вернет 17
            System.out.println(nextEdge(8, 10));
            // текст 9 - вернет 855
            System.out.println(sumOfCubes (new int[]{1, 5 , 9}));
            // текст 10 - вернет true
            System.out.println(abcmath(5, 2 , 1));
        }

}
