package FX.FXWordLink;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

class Dictionary {
	private List<String> dict;
	public Dictionary() throws IOException {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(new File(getClass().getResource("words_alpha.txt").toURI())));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		String str;
		dict = new ArrayList<String> ();
		while ((str = in.readLine()) != null) {
			dict.add(str);
		}
		in.close();
	}
	public boolean contains (Object o) {
		return dict.contains(o);
	}
	public List<String> getDict() {
		return dict;
	}
	public void printDict() {
		int i = 1;
		for (String s: dict) {
			System.out.println(""+(i++)+" -- " + s);
		}
	}
}