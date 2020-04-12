package com.renbeynolds.eplranking;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class EPLRanking {

    private static CommandLine parseArgs(String[] argv) {
        Options options = new Options();

        Option input = new Option("d", "dataDir", true, "path to directory of data csvs");
        input.setRequired(true);
        options.addOption(input);

        input = new Option("f", "firstSeasonStartYear", true, "start of season year for the first season of data to use");
        input.setRequired(true);
        options.addOption(input);

        input = new Option("l", "lastSeasonStartYear", true, "start of season year for the last season of data to use");
        input.setRequired(true);
        options.addOption(input);

        input = new Option("r", "rank", false, "show team rankings based on historic data");
        input.setRequired(false);
        options.addOption(input);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        try {
            CommandLine cmd = parser.parse(options, argv);

            if(Integer.parseInt(cmd.getOptionValue("lastSeasonStartYear")) < Integer.parseInt(cmd.getOptionValue("firstSeasonStartYear"))) {
                throw new ParseException("lastSeasonStartYear must be >= firstSeasonStartYear");
            }

            return cmd;
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("java -jar eplranking.jar", options);
            System.exit(1);
            return null;
        }

    }

    public static void main(String[] argv) {

        CommandLine args = parseArgs(argv);

        // System.out.println(args.hasOption("rank"));

        Simulator simulator = new Simulator(
            args.getOptionValue("dataDir"),
            Integer.parseInt(args.getOptionValue("firstSeasonStartYear")),
            Integer.parseInt(args.getOptionValue("lastSeasonStartYear"))
        );
        
        simulator.getRankings();

    }

}