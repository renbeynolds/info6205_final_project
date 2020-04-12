package com.renbeynolds.eplranking;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PoissonTest {

    @Test
    public void canComputeFactorial(){
        assertEquals(1, Poisson.factorial(0));
        assertEquals(24, Poisson.factorial(4));
        assertEquals(720, Poisson.factorial(6));
        assertEquals(39916800, Poisson.factorial(11));
    }

    @Test
    public void canComputePmf() {
        assertEquals(0.153, Poisson.pmf(0, 1.875), .001);
        assertEquals(0.334, Poisson.pmf(1, 1.5), .001);
        assertEquals(0.268, Poisson.pmf(2, 1.842), .001);
    }

}