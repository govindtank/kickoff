package com.massivedisaster.kickoff.util;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

@SuppressWarnings("deprecation")
public class Cli{

	private static final Logger log = Logger.getLogger(Cli.class.getName());

	private String[] args = null;
	private Options options = new Options();

	public Cli(String[] args) {

		this.args = args;

		options.addOption(new Option("h", "help", false, "Show Help."));
		options.addOption(new Option("u", "update", false, "Update the template project."));
		options.addOption(new Option("g", "generate", true, "Generate a new project based on a configuration file."));
		options.addOption(new Option("o", "open", false, "Open a website to create a new project configuration file."));
	}

	 public void parse() {
		CommandLineParser parser = new BasicParser();

		CommandLine cmd = null;
		try {
			cmd = parser.parse(options, args, true);

			if (cmd.hasOption("h")){
				help();
			} else if (cmd.hasOption("o")) {
				open();
			} else if(!cmd.hasOption("g")){
				log.log(Level.SEVERE, "Missing g option");
				help();
			}

		} catch (ParseException e) {
			log.log(Level.SEVERE, "Failed to parse comand line properties", e);
			help();
		}
	}

	 public CommandLine getOptions(){
		CommandLineParser parser = new BasicParser();
		try {
			return parser.parse(options, args, true);
		} catch (ParseException e) {
			e.printStackTrace();
			log.log(Level.SEVERE, "Failed to parse comand line properties", e);
			help();
			return null;
		}

	}

	private void help() {
		// This prints out some help
		HelpFormatter formater = new HelpFormatter();

		formater.printHelp("Kickoff", options);
		System.exit(0);
	}

	private void open() {
		try {
			Desktop.getDesktop().browse(new URI(Const.WEBSITE));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
}