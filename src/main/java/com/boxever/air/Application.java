package com.boxever.air;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import com.boxever.air.seatarrange.SeatArrangement;

/**
 * Application main entry point class to read the file and assign seat for
 * passengers
 * 
 * @author dkumar
 *
 */
public class Application {

	@Option(name = "-f", aliases = { "--file" }, required = true, usage = "File with pax sitting details.")
	private String m_filePath;

	/**
	 * Entry point
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new Application().run(args);
	}

	public void run(String[] args) {
		CmdLineParser parser = new CmdLineParser(this);
		parser.setUsageWidth(80);

		try {
			parser.parseArgument(args);

			SeatArrangement arrangement = new SeatArrangement();
			String arrangedSeatMap = arrangement
					.arrangeSeatForPaxFromFile(m_filePath);
			StringBuilder sb = new StringBuilder();
			sb.append(SeatArrangement.SEPERATER).append("Final Seat Map ")
					.append(SeatArrangement.SEPERATER).append(arrangedSeatMap)
					.append(arrangement.satisfiedPaxPercentage() + "%");
			System.out.print(sb.toString());

		} catch (CmdLineException e) {
			System.err
					.println("No arguments were provided. Please see usage below:");
			parser.printUsage(System.err);
			System.err.println();
			System.err
					.println(" Example: java -cf SeatArrangement --file FilePath");
			return;
		} catch (Exception e) {
			System.out.println("Error while reading data from file. "
					+ e.getMessage());
		}
	}

}
