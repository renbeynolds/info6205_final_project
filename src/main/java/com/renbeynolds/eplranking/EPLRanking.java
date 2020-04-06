package com.renbeynolds.eplranking;

import java.util.List;

public class EPLRanking {
    public static void main(String[] args) {
        for(int i = 0; i<1; i++) {
            List<MatchInfo> matches = CSVReader.read(args[i]);
        }
    }
}