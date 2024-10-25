/*
 * Да се напише метод кој ќе прима еден цел број и ќе ја печати неговата репрезентација како Римски број.

Пример

    Aко ако се повика со парамететар 1998, излезот треба да биде MCMXCVIII.

 */

 import java.util.Scanner;
import java.util.stream.IntStream;

public class RomanConverterTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        IntStream.range(0, n)
                .forEach(x -> System.out.println(RomanConverter.toRoman(scanner.nextInt())));
        scanner.close();
    }
}


class RomanConverter {
    /**
     * Roman to decimal converter
     *
     * @param n number in decimal format
     * @return string representation of the number in Roman numeral
     */
    public static String toRoman(int n) {
        // your solution here
        int [] val= {1000,900,500,400,100,90,50,40,10,9,5,4,1};
        String [] romanLetters= {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};
        StringBuilder roman = new StringBuilder();
        for (int i=0; i<val.length; i++){
            while (n>=val[i]){
                n-=val[i];
                roman.append(romanLetters[i]);
            }
        }
        System.out.print(roman.toString());
        return "";
    }

}
