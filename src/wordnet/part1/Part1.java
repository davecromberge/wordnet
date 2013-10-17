package wordnet.part1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.ISynsetID;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.item.Pointer;
import wordnet.Pair;
import wordnet.WordNet;

public class Part1 {
	/*
	 * Give a detailed account of similarities and differences among the
	 * following set of lexemes: imitation, synthetic, artificial, fake, simulated
	 */
	private final WordNet wordnet;
	private final Map<ISynsetID, List<Pair<IWord, Integer>>> synsets;
	private final Set<String> results;

	public Part1(String path) {
		wordnet = new WordNet(path);
		results = new HashSet<String>();
		synsets = new HashMap<ISynsetID, List<Pair<IWord, Integer>>>();
	}

	public void run(String[] args) {

		for (String word : args) {
			for (POS pos : POS.values()) {
				expandHypernyms(word, pos);
			}
		}
		
		for (String result : results) {
			System.out.println(result);
		}

	}

	private void reportMatch(IWord word, int level,
			List<Pair<IWord, Integer>> hyponyms) {
		Pair<IWord, Integer> closest = null;

		for (Pair<IWord, Integer> related : hyponyms) {
			if (closest == null
					|| (level - related.getRight()) < closest.getRight())
				closest = new Pair<IWord, Integer>(related.getLeft(), level
						- related.getRight());
		}

		results.add(String.format(
				"The word: %s [%s] is close to the word: %s [%s]",
				word.getLemma(), word.getPOS(), closest.getLeft().getLemma(),
				closest.getLeft().getPOS()));
	}

	private void expandHypernyms(ISynset current, IWord word, int level) {

		if (current == null)
			return;
		else if (synsets.containsKey(current.getID())) {
			reportMatch(word, level, synsets.get(current.getID()));

			synsets.get(current.getID()).add(
					new Pair<IWord, Integer>(word, level));
		} else {
			List<Pair<IWord, Integer>> pairs = new ArrayList<Pair<IWord, Integer>>();
			pairs.add(new Pair<IWord, Integer>(word, level));
			synsets.put(current.getID(), pairs);
		}

		Pointer pointer = null;
		if (current.getPOS().equals(POS.ADJECTIVE)) {
			pointer = Pointer.DERIVATIONALLY_RELATED;
		} else {
			pointer = Pointer.HYPERNYM;
		}

		for (ISynsetID sid : current.getRelatedSynsets(pointer)) {
			expandHypernyms(wordnet.get().getSynset(sid), word, level + 1);
		}
	}

	private void expandHypernyms(String searchWord, POS pos) {
		IIndexWord idxWord = wordnet.get().getIndexWord(searchWord, pos);
		if (idxWord == null)
			return;

		for (IWordID wordId : idxWord.getWordIDs()) {
			IWord word = wordnet.get().getWord(wordId);
			expandHypernyms(word.getSynset(), word, 0);
		}
	}
}
