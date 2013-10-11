package wordnet;

import java.io.IOException;

import wordnet.part1.Part1;

import edu.mit.jwi.IRAMDictionary;

public class Program {

	private static IRAMDictionary dict;
	private static String lineSeparator = System.getProperty("line.separator");

	public static void main(String[] args) throws IOException {

		/*
		 * 1 Give a detailed account of similarities and differences among the
		 * following set of lexemes: imitation, synthetic, artificial, fake,
		 * simulated 2 The WordNet entry for the noun bat lists six distinct
		 * senses. Cluster these senses by using the definitions of homonymy and
		 * polysemy. For any senses that are polysemous, give an argument as to
		 * how the sense are related. 3 Using WordNet, simulate the original
		 * Lesk word overlap disambiguation algorithm. Assume that the words are
		 * to be disambiguated one at a time, from L to R, and that the results
		 * from earlier decisions are used later in the process. The phrase is
		 * "Time flies like an arrow"
		 */
/*
		if (args == null || args.length <= 1) {
			printHelp();
			System.exit(0);
		}
		
		*/

		String path = "/media/dev/WordNet-3.0";

		String[] words = { "imitation", "synthetic", "artificial", "fake",
				"simulated" };

		new Part1(path).run(words);
		
		System.out.println("Completed.");
	}

	private static void printHelp() {
		StringBuilder sb = new StringBuilder();
		sb.append("Usage:" + lineSeparator);
		System.out.println(sb.toString());
	}
}
