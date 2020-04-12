package com.renbeynolds.eplranking;

public class Poisson {

    // n > 20 will overflow long
    static long factorial(int n) {
        if (n <= 2) {
            return n;
        }
        return n * factorial(n - 1);
    }

    static double pmf(int X, double mu) {
        return Math.exp(-1 * mu) * (Math.pow(mu, X) / factorial(X));
    }

}