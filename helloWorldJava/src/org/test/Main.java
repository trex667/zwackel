package org.test;

public class Main {

    public static void main(String[] args) {
        System.out.println("Sec\tMeters\tFeet ");
        for (int t = 1; t <= 10; t += 1) {
            System.out.println(t);
            System.out.println(distanceFell(t));
            System.out.println(metersToFeet(t));
        }
    }

    public static double distanceFell(int t) {
        double conv = ((1 / 2) * 9.8 * ((t) ^ 2));
        return (conv);
    }

    public static double metersToFeet(double conv) {
        double convFeet = (conv / 0.3048);
        return (convFeet);
    }
}
