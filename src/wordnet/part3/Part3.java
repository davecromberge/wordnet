package wordnet.part3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import wordnet.WordNet;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;

public class Part3 {
	/*
	 * 3 Using WordNet, simulate the original Lesk word overlap disambiguation
	 * algorithm. Assume that the words are to be disambiguated one at a time,
	 * from L to R, and that the results from earlier decisions are used later
	 * in the process. The phrase is "Time flies like an arrow"
	 */

	private final WordNet wordnet;
	private final Set<String> results;

	public Part3(String path) {
		wordnet = new WordNet(path);
		results = new HashSet<String>();
	}

	public void run(String[] args) {

		for (String word : args) {
			results.add(String.format("The WSD sense for %s is %s", word,
					simplifiedLesk(word, args)));
		}

		for (String result : results) {
			System.out.println(result);
		}

	}

	private String[] getSignature(IWordID sense) {
		IWord word = wordnet.get().getWord(sense);
		ArrayList<String> signature = new ArrayList<String>(Arrays.asList(word
				.getSynset().getGloss().split("\\s+")));
		for (IWord example : word.getSynset().getWords())
			signature.add(example.getLemma());
		return signature.toArray(new String[signature.size()]);
	}

	private POS simplifiedLesk(String word, String[] context) {
		int maxOverlap = 0;
		POS bestSense = POS.NOUN;

		for (POS pos : POS.values()) {

			IIndexWord indexWord = wordnet.get().getIndexWord(word, pos);
			if (indexWord != null) {
				bestSense = indexWord.getPOS();

				for (IWordID sense : indexWord.getWordIDs()) {
					String[] signature = getSignature(sense);
					int overlap = computeOverlap(signature, context);

					if (overlap > maxOverlap) {
						maxOverlap = overlap;
						bestSense = sense.getPOS();
					}
				}
			}
		}
		return bestSense;
	}

	private int computeOverlap(String[] signature, String[] context) {

		int count = 0;
		for (int i = 0; i < signature.length; i++) {
			for (int j = 0; j < context.length; j++) {
				if (signature[i].equals(context[j])) {
					count++;
					break;
				}
			}
		}

		String[] result = new String[count];
		count = 0;
		for (int i = 0; i < signature.length; i++) {
			for (int j = 0; j < context.length; j++) {
				if (signature[i].equals(context[j])) {
					result[count++] = signature[i];
					break;
				}
			}
		}

		return result.length;
	}
}
