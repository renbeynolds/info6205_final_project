package com.renbeynolds.eplranking;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DataLoaderTest {

    @Test
    public void canGetColumn(){
        String[] columnHeaders = new String[]{"Date", "Home", "Away", "FTHG", "FTAG" };
        assertEquals(0, DataLoader.getColumn(columnHeaders, "Date"));
        assertEquals(1, DataLoader.getColumn(columnHeaders, "Home"));
        assertEquals(3, DataLoader.getColumn(columnHeaders, "FTHG"));
        assertEquals(-1, DataLoader.getColumn(columnHeaders, "Missing"));
    }

}