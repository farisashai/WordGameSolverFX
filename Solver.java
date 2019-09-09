package FX.FXWordLink;
import java.io.IOException;
import java.util.*;
public class Solver {
	static Scanner sc = new Scanner(System.in);
	static java.util.Dictionary dict;
	public static List<String> getAllWords(String chars, int length) throws IOException{
		String[] letters = chars.replaceAll("[^A-Za-z]", "").toUpperCase().split("");
		int numPossible = (int)(Math.pow(letters.length, length));
		List<String> possible = new ArrayList<String>();
		for (int i = 0; i < numPossible; i++) {
			int i1 = Integer.parseInt(Integer.toString(i, letters.length));
			String i2 = String.format("%0"+length+"d", i1);
			String str = convert(letters,intArray(i2.split("")));
			if (!possible.contains(str) && allValidLetters(str,letters))
				possible.add(str.toLowerCase());
		}
		Collections.sort(possible);
		noCopies(possible);
		compareWithDict(possible,new Dictionary().getDict());
		return possible;
	}
	private static void compareWithDict(List<String> possible, List<String> list) {
		List<String> valid = new ArrayList<String>();
		for (String x: possible)
			if (list.contains(x))
				valid.add(x);
		possible.clear();
		possible.addAll(valid);
	}
	public static List<String> getAllWords (String chars) throws IOException {
		return getAllWords(chars,chars.length());
	}
	public static void noCopies (List<String> strings) {
		List<String> newList = new ArrayList<String>();
		for (String s: strings)
			if (!newList.contains(s))
				newList.add(s);
		strings.clear();
		strings.addAll(newList);
	}
	private static boolean allValidLetters(String str, String[] letters) {
		int[] numOfEachLetter = new int[26];
		for (String s: letters)
			numOfEachLetter[(s.charAt(0))-65]++;
		for (int i = 0; i < str.length(); i++)
			numOfEachLetter[(str.charAt(i))-65]--;
		for (int i: numOfEachLetter)
			if (i < 0)
				return false;
		return true;
	}
	private static String convert(String[] letters, int[] intArray) {
		String str = "";
		for (int i = 0; i < intArray.length; i++)
			str += letters[intArray[i]];
		return str;
	}
	private static int[] intArray(String[] split) {
		int[] arr = new int[split.length];
		for (int i = 0; i < arr.length; i++)
			arr[i] = Integer.parseInt(split[i]);
		return arr;
	}
}