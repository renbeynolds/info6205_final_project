package com.renbeynolds.eplranking;

public class Poisson {

    // n > 20 will overflow long but
    // we only consider max score of 10
    static long factorial(int n) {
        if (n == 0) {
            return 1;
        } else if (n <= 2) {
            return n;
        }
        return n * factorial(n - 1);
    }

    static double pmf(int X, double mu) {
        return Math.exp(-1 * mu) * (Math.pow(mu, X) / factorial(X));
    }

}