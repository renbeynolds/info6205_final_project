package com.renbeynolds.eplranking;

import org.junit.Test;
import static org.junit.Assert.*;

import java.beans.Transient;

public class PoissonTest {

    @Test
    public void canComputeFactorial(){
        assertEquals(24, Poisson.factorial(4));
        assertEquals(720, Poisson.factorial(6));
        assertEquals(39916800, Poisson.factorial(11));
    }

    @Test
    public void canComputePmf() {
        assertEquals(0.334, Poisson.pmf(1, 1.5), .001);
        assertEquals(0.268, Poisson.pmf(2, 1.842), .001);
    }

}