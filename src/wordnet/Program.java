package wordnet;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;

public class Program {
	public static void main(String[] args) throws IOException {

		/* 1 Give a detailed account of similarities and differences among the following set of lexemes:
		 		imitation, synthetic, artificial, fake, simulated
		   2 The WordNet entry for the noun bat lists six distinct senses.  Cluster these
		     senses by using the definitions of homonymy and polysemy.  For any senses that
		     are polysemous, give an argument as to how the sense are related.
		   3 Using WordNet, simulate the original Lesk word overlap disambiguation algorithm.
		     Assume that the words are to be disambiguated one at a time, from L to R,
		     and that the results from earlier decisions are used later in the process.  
		     The phrase is "Time flies like an arrow"
		 */
		
		String wnhome = "/home/dave/development/WordNet-3.0";

		// construct the URL to the Wordnet dictionary directory
		String path = wnhome + File.separator + "dict";
		URL url = new URL("file", null, path);
		// construct the dictionary object and open it
		IDictionary dict = new Dictionary(url);
		dict.open();

		// look up first sense of the word " dog "
		IIndexWord idxWord = dict.getIndexWord(" dog ", POS.NOUN);
		IWordID wordID = idxWord.getWordIDs().get(0);
		IWord word = dict.getWord(wordID);
		System.out.println(" Id = " + wordID);
		System.out.println(" Lemma = " + word.getLemma());
		System.out.println(" Gloss = " + word.getSynset().getGloss());

	}
}
