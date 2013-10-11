package wordnet.part1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	private final WordNet wordnet;

	public Part1(String path) {
		wordnet = new WordNet(path);
	}

	public void run(String[] args) {
		List<Map<IWord, List<ISynset>>> semanticRelations = new ArrayList<Map<IWord, List<ISynset>>>();

		for (String word : args) {
			semanticRelations.add(getHypernyms(word));
		}

		for (Map<IWord, List<ISynset>> relation : semanticRelations) {
			for (IWord word : relation.keySet()) {
				for (Map<IWord, List<ISynset>> otherRelation : semanticRelations) {
					if (relation == otherRelation)
						continue;
					Pair<IWord, Double> similarity = getPathSimilarity(
							word, otherRelation);
					if (similarity.getLeft() == null)
						continue;

					System.out.printf("Similarity between %s and %s is %f",
							word.getLemma(), similarity.getLeft().getLemma(), similarity.getRight());
					System.out.println();
				}
			}
		}
	}

	private Pair<IWord, Double> getPathSimilarity(IWord word,
			Map<IWord, List<ISynset>> other) {

		int shortestPath = 1000;
		IWord matchWord = null;

		for (Map.Entry<IWord, List<ISynset>> pair : other.entrySet()) {
			int path = 0;
			for (ISynset current : pair.getValue()) {
				String sid1 = word.getSynset().getID().toString();
				String sid2 = current.getID().toString();
				if (sid1.equals(sid2) && path <= shortestPath) {
					shortestPath = path;
					matchWord = pair.getKey();
					break;
				}
			}
		}

		return new Pair<IWord, Double>(matchWord,
				(double) (1 / (shortestPath + 1)));
	}

	private void expandHypernyms(ISynset current, List<ISynset> synsets) {
		if (current == null)
			return;
		else if (!synsets.contains(current))
			synsets.add(current);

		if (current.getPOS().equals(POS.ADJECTIVE)) {
			for (ISynsetID sid : current.getRelatedSynsets(Pointer.DERIVATIONALLY_RELATED)) {
				expandHypernyms(wordnet.get().getSynset(sid), synsets);
			}
		} else {
			for (ISynsetID sid : current.getRelatedSynsets(Pointer.HYPERNYM)) {
				expandHypernyms(wordnet.get().getSynset(sid), synsets);
			}
		}
	}

	private void expandHypernyms(Map<IWord, List<ISynset>> map,
			String searchWord, POS pos) {
		IIndexWord idxWord = wordnet.get().getIndexWord(searchWord, pos);
		if (idxWord == null)
			return;

		for (IWordID wordId : idxWord.getWordIDs()) {
			IWord word = wordnet.get().getWord(wordId);
			List<ISynset> synsets = new ArrayList<ISynset>();
			expandHypernyms(word.getSynset(), synsets);
			map.put(word, synsets);
		}
	}

	private Map<IWord, List<ISynset>> getHypernyms(String searchWord) {
		Map<IWord, List<ISynset>> map = new HashMap<IWord, List<ISynset>>();

		for (POS pos : POS.values()) {
			expandHypernyms(map, searchWord, pos);
		}
		return map;
	}
}
