package com.renbeynolds.eplranking;

import com.renbeynolds.eplranking.cli.Compete;
import com.renbeynolds.eplranking.cli.Rank;
import com.renbeynolds.eplranking.cli.Table;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.Spec;

@Command(
    name = "epl",
    mixinStandardHelpOptions = true,
    synopsisSubcommandLabel = "COMMAND",
    version = "1.0.0",
    subcommands = {Rank.class, Compete.class, Table.class}
)
class EPL implements Runnable {

    @Spec CommandSpec spec;
    public void run() {
        throw new ParameterException(spec.commandLine(), "Missing required subcommand");
    }

    public static void main(String... args) { // bootstrap the application
        System.exit(new CommandLine(new EPL()).execute(args));
    }
}