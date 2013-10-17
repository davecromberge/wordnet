package wordnet;

import java.io.IOException;
import java.util.ArrayList;

import wordnet.part1.Part1;
import wordnet.part2.Part2;
import wordnet.part3.Part3;

public class Program {

	private static String lineSeparator = System.getProperty("line.separator");

	public static void main(String[] args) throws IOException {

		if (args == null || args.length <= 1) {
			printHelp();
			System.exit(0);
		}

		String choice = "1";
		String path = "/home/dave/development/WordNet-3.0";

		ArrayList<String> rest = new ArrayList<String>();

		for (int i = 0; i < args.length; i++) {
			if (i == 0)
				choice = args[i];
			else if (i == 1) {
				path = args[i];
			} else
				rest.add(args[i]);
		}

		switch (choice) {
		case "1":
			new Part1(path).run(rest.toArray(new String[rest.size()]));
			break;
		case "2":
			new Part2(path).run(rest.toArray(new String[rest.size()]));
			break;
		case "3":
			new Part3(path).run(rest.toArray(new String[rest.size()]));
			break;
		}
		System.out.println("Exiting...");
	}

	private static void printHelp() {
		StringBuilder sb = new StringBuilder();
		sb.append("Usage:" + lineSeparator);
		sb.append("\t [1-3] indicates which sub program to run" + lineSeparator);
		sb.append("\t [path] indicates path on disk to WordNet" + lineSeparator);
		sb.append("\t [Word1, Word2 ...] indicates arguments to problem"
				+ lineSeparator);
		sb.append(lineSeparator);
		sb.append("Example part1:" + lineSeparator);
		sb.append("\tjava -jar wordnet.jar '1', '/usr/bin/WordNet-3.0', 'imitation', 'synthetic', 'artificial', 'fake', 'simulated'"
				+ lineSeparator);
		sb.append("Example part2:" + lineSeparator);
		sb.append("\tjava -jar wordnet.jar '1', '/usr/bin/WordNet-3.0', 'bat'"
				+ lineSeparator);
		sb.append("Example part3:" + lineSeparator);
		sb.append("\tjava -jar wordnet.jar '1', '/usr/bin/WordNet-3.0', 'Time', 'flies', 'like', 'an', 'arrow'"
				+ lineSeparator);

		System.out.println(sb.toString());
	}
}
