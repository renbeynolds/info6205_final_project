package com.renbeynolds.eplranking;

import java.util.Set;

public class EPLRanking {
    public static void main(String[] args) {

        Set<MatchInfo> matches;
        try {
            String dataDir = args[0];
            Integer firstSeasonStartYear = Integer.valueOf(args[1]);
            Integer lastSeasonStartYear = Integer.valueOf(args[2]);
            matches = DataLoader.loadData(dataDir, firstSeasonStartYear, lastSeasonStartYear);
        } catch(Exception e) {
            System.err.println("USAGE: java -jar /path/to/data/directory <firstSeasonStartYear> <lastSeasonStartYear>");
        }

    }
}