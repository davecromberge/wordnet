package wordnet.part2;

import java.util.HashSet;
import java.util.Set;

import wordnet.WordNet;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynsetID;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.item.Pointer;

public class Part2 {

	/*
	 * The WordNet entry for the noun bat lists six distinct senses. Cluster
	 * these senses by using the definitions of homonymy and polysemy. For any
	 * senses that are polysemous, give an argument as to how the sense are
	 * related.
	 */

	private final WordNet wordnet;
	private final Set<String> results;

	public Part2(String path) {
		wordnet = new WordNet(path);
		results = new HashSet<String>();
	}

	public void run(String[] args) {

		for (String word : args) {
			for (POS pos : POS.values()) {
				cluster(word, pos);
			}
		}

		for (String result : results) {
			System.out.println(result);
		}

	}

	private void cluster(String searchWord, POS pos) {
		IIndexWord idxWord = wordnet.get().getIndexWord(searchWord, pos);
		if (idxWord == null)
			return;

		for (IWordID wordId : idxWord.getWordIDs()) {
			IWord word = wordnet.get().getWord(wordId);
			for (ISynsetID synsetID : word.getSynset().getRelatedSynsets(
					Pointer.MERONYM_PART))
				for (IWord part : wordnet.get().getSynset(synsetID).getWords()) {
					results.add(String.format(
							"%s [%s] is a meronym of %s [%s]", part.getLemma(),
							part.getPOS(), word.getLemma(), word.getPOS()));
				}
		}
	}
}
