package com.renbeynolds.eplranking;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import com.renbeynolds.eplranking.models.MatchModel;

public class DataLoader {

    public static Set<MatchModel> loadData(String dataDir, int firstSeasonStartYear, int lastSeasonStartYear) {
        Set<MatchModel> matches = new HashSet<MatchModel>();
        for(int year = firstSeasonStartYear; year < lastSeasonStartYear + 1; year++) {
            matches.addAll(readFile(Paths.get(dataDir, String.format("%d-%d.csv", year, year + 1)).toString()));
        }
        return matches;
    }

    private static Set<MatchModel> readFile(String filename) {
        String line = "";
        String cvsSplitBy = ",";
        Set<MatchModel> result = new HashSet<MatchModel>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            int row = 0;
            while ((line = br.readLine()) != null) {
                if(row > 0) {
                    String[] data = line.split(cvsSplitBy);
                    result.add(new MatchModel(data));
                }
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}