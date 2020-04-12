package com.renbeynolds.eplranking;

public class EPLRanking {

    private static Args parseArgs(String[] args) {
        try {
            if (args.length != 3) {
                throw new Exception("Incorrect number of arguments!");
            }
            String dataDir = args[0];
            int firstSeasonStartYear = Integer.parseInt(args[1]);
            int lastSeasonStartYear = Integer.parseInt(args[2]);
            if (firstSeasonStartYear > lastSeasonStartYear) {
                throw new Exception("firstSeasonStartYear must not be greater than lastSeasonStartYear!");
            }
            return new Args(dataDir, firstSeasonStartYear, lastSeasonStartYear);
        } catch (Exception e) {
            System.err.println("USAGE: java -jar /path/to/data/directory <firstSeasonStartYear> <lastSeasonStartYear>");
            System.exit(1);
            return null;
        }
    }

    private static class Args {
        private Args(String dataDir, int firstSeasonStartYear, int lastSeasonStartYear) {
            this.dataDir = dataDir;
            this.firstSeasonStartYear = firstSeasonStartYear;
            this.lastSeasonStartYear = lastSeasonStartYear;
        }

        String dataDir;
        int firstSeasonStartYear;
        int lastSeasonStartYear;
    }

    public static void main(String[] args) {

        Args parsedArgs = parseArgs(args);

        Simulator simulator = new Simulator(
            parsedArgs.dataDir,
            parsedArgs.firstSeasonStartYear,
            parsedArgs.lastSeasonStartYear
        );
        
        simulator.getRankings();

    }

}