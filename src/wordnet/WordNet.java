package wordnet;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import edu.mit.jwi.IRAMDictionary;
import edu.mit.jwi.RAMDictionary;
import edu.mit.jwi.data.ILoadPolicy;

public class WordNet {

	private final String path;
	private IRAMDictionary dict;

	public WordNet(String path) {
		if (path.isEmpty())
			throw new IllegalArgumentException("Path cannot be empty!");
		this.path = path;
	}

	public IRAMDictionary get() {
		if (dict == null) {
			dict = InitDictionary(path);
		}
		return dict;
	}

	private static IRAMDictionary InitDictionary(String path) {
		// construct the URL to the WordNet dictionary directory
		IRAMDictionary result = null;
		try {
			URL url = new URL("file", null, path + File.separator + "dict");
			result = new RAMDictionary(url, ILoadPolicy.NO_LOAD);
			result.open();
		} catch (MalformedURLException ex) {
			System.exit(1);
		} catch (IOException ex) {
			System.out
					.println("WordNet dictionary files could not be located.");
			System.exit(1);
		}
		return result;
	}
}
