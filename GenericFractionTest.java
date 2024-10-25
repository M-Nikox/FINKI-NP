/*
 * Треба да се развие генеричка класа за работа со дропки. Класата GenericFraction има два генерички параметри T и U кои мора да бидат од некоја класа која наследува од класата Number. GenericFraction има две променливи:

    numerator - броител
    denominator - именител.

Треба да се имплементираат следните методи:

    GenericFraction(T numerator, U denominator) - конструктор кој ги иницијализира броителот и именителот на дропката. Ако се обидиме да иницијализираме дропка со 0 вредност за именителот треба да се фрли исклучок од тип ZeroDenominatorException
    GenericFraction<Double, Double> add(GenericFraction<? extends Number, ? extends Number> gf) - собирање на две дропки
    double toDouble() - враќа вредност на дропката како реален број
    toString():String - ја печати дропката во следниот формат [numerator] / [denominator], скратена (нормализирана) и секој со две децимални места.

 */

 import java.util.Scanner;

public class GenericFractionTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double n1 = scanner.nextDouble();
        double d1 = scanner.nextDouble();
        float n2 = scanner.nextFloat();
        float d2 = scanner.nextFloat();
        int n3 = scanner.nextInt();
        int d3 = scanner.nextInt();
        try {
            GenericFraction<Double, Double> gfDouble = new GenericFraction<Double, Double>(n1, d1);
            GenericFraction<Float, Float> gfFloat = new GenericFraction<Float, Float>(n2, d2);
            GenericFraction<Integer, Integer> gfInt = new GenericFraction<Integer, Integer>(n3, d3);
            System.out.printf("%.2f\n", gfDouble.toDouble());
            System.out.println(gfDouble.add(gfFloat));
            System.out.println(gfInt.add(gfFloat));
            System.out.println(gfDouble.add(gfInt));
            gfInt = new GenericFraction<Integer, Integer>(n3, 0);
        } catch(ZeroDenominatorException e) {
            System.out.println(e.getMessage());
        }

        scanner.close();
    }

}

// вашиот код овде
class GenericFraction<T,U> {
    T numerator;
    U denominator;

    public double toDouble() {
        return getNumerator() / getDenominator();
    }

    public Double getNumerator() {
        return ((Number) numerator).doubleValue();
    }

    public Double getDenominator() {
        return ((Number) denominator).doubleValue();
    }

    public double nzs(double a, double b) {
        return b == 0 ? a : nzs(b, a % b);
    }

    @Override
    public String toString() {
        double nzs = nzs(getNumerator(), getDenominator());
        return String.format("%.2f / %.2f", getNumerator() / nzs, getDenominator() / nzs);
    }

    GenericFraction(T numerator, U denominator) throws ZeroDenominatorException{
        if (denominator instanceof Number && ((Number) denominator).doubleValue() == 0) {
            throw new ZeroDenominatorException();
        } else {
            this.numerator=numerator;
            this.denominator=denominator;
        }
    }

    public GenericFraction<Double, Double> add(GenericFraction<? extends Number, ? extends Number> gf) throws ZeroDenominatorException {
        GenericFraction<Double, Double> r = null;
        try {
            Double b = getDenominator() * gf.getDenominator();
            Double a = (getNumerator() * (b / getDenominator()) + gf.getNumerator() * (b / gf.getDenominator()));
            r = new GenericFraction<Double, Double>(a, b);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return r;
    }
}

class ZeroDenominatorException extends Exception{
    ZeroDenominatorException(){
        super("Denominator cannot be zero");
    }
}