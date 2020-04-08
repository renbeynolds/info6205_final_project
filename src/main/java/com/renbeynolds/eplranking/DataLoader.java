package com.renbeynolds.eplranking;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class DataLoader {

    public static Set<MatchInfo> loadData(String dataDir, int firstSeasonStartYear, int lastSeasonStartYear) {
        Set<MatchInfo> matches = new HashSet<MatchInfo>();
        for(int year = firstSeasonStartYear; year < lastSeasonStartYear + 1; year++) {
            matches.addAll(readFile(Paths.get(dataDir, String.format("%d-%d.csv", year, year + 1)).toString()));
        }
        return matches;
    }

    private static Set<MatchInfo> readFile(String filename) {
        String line = "";
        String cvsSplitBy = ",";
        Set<MatchInfo> result = new HashSet<MatchInfo>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            int row = 0;
            while ((line = br.readLine()) != null) {
                if(row > 0) {
                    String[] data = line.split(cvsSplitBy);
                    result.add(new MatchInfo(data));
                }
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}