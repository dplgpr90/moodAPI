package energyAPIImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class EnergyIndex {

	// Gets upper_case value according to its weight
	private double getUppercaseValue(double UPPER_CASE, String text) {

		// Replaces punctuation and more than one blank space with one blank space
		text = text.replaceAll("[ \\p{Punct}]", " ");
		text = text.replaceAll(" {2,}", " ");
		
		// Obtains words from the text
		String[] words = text.split(" ");

		// Counts how many words are in upper case
		int wordInUpperCase = 0;
		for (int i = 0; i < words.length; i++) {
			if (words[i].toUpperCase().equals(words[i])) {
				// The word is in upper case
				wordInUpperCase++;
			}
		}

		// Calculates returned value
		double value;
		if (wordInUpperCase == 0) {
			// There are no words in upper case
			value = 0;
		} else {
			// There are words in upper case, calculate the rate
			double difference = (words.length - wordInUpperCase) * 5;
			if (difference > 100) {
				difference = 100;
			}
			double rate = 100 - difference;
			value = (UPPER_CASE / 100) * rate;
		}

		return value;
	}

	// Gets same_word value according to its weight
	private double getSameWordValue(double SAME_WORD, String text) {

		// Replaces punctuation and more than one blank space with one blank space
		text = text.replaceAll("[ \\p{Punct}]", " ");
		text = text.replaceAll(" {2,}", " ");
		
		// Obtains words from the text
		String[] words = text.split(" ");

		// For each word:
		// 1) Deletes punctuation and spaces;
		// 2) Replaces all the letters that occur more than twice with two
		// occurrences of the letter
		for (int i = 0; i < words.length; i++) {
			words[i] = words[i].replaceAll("[ \\p{Punct}]", "");
			words[i] = replaceRepeatedLetter(words[i].toLowerCase());
		}

		// Checks presence of same string consecutively
		double repetitions = 0;
		for (int i = 0; i < words.length;) {
			int count = 0;
			for (int j = i; j < words.length; j++) {
				if (!words[i].equals(words[j])) {
					break;
				}
				count++;
			}
			if (count > 1) {
				// There are repetition of a word
				repetitions += count;
			}
			i += count;
		}

		// Calculates the value
		double rate;
		if (repetitions != 0) {
			// There are repetitions
			if (repetitions / words.length < 0.25) {
				rate = 25;
			} else {
				rate = 100 * (repetitions / words.length);
			}
		} else {
			// There are no repetitions
			rate = 0;
		}

		return (SAME_WORD / 100) * rate;
	}

	// Gets multiplied_letter value according to its weight
	private double getMultipliedLetterValue(double MULTIPLIED_LETTER,
			String text) {
		
		// Replaces punctuation and more than one blank space with one blank space
		text = text.replaceAll("[ \\p{Punct}]", " ");
		text = text.replaceAll(" {2,}", " ");

		// Obtains words from the text
		String[] words = text.split(" ");

		// For each word deletes punctuation and spaces;
		for (int i = 0; i < words.length; i++) {
			words[i] = words[i].replaceAll("[ \\p{Punct}]", "");
		}

		// Counts repeated letters for each word and save the number
		List<Integer> repeatedLetters = new ArrayList<Integer>();
		for (int i = 0; i < words.length; i++) {
			int letterOccurrences = countRepeatedLetter(words[i].toLowerCase());
			if (letterOccurrences != 0) {
				repeatedLetters.add(letterOccurrences);
			}
		}

		// Returned value
		double value = 0;

		// Assigns weight for each word and sum to value
		if (repeatedLetters.size() != 0) {
			double singleWordWeight = MULTIPLIED_LETTER
					/ repeatedLetters.size();
			Iterator<Integer> iterator = repeatedLetters.iterator();
			while (iterator.hasNext()) {
				double rate = 12.5 * iterator.next();
				if (rate > 100) {
					rate = 100;
				}
				value += (singleWordWeight / 100) * rate;
			}
		}

		return value;
	}

	// Counts occurrence of a repeated letter in the word.
	private int countRepeatedLetter(String word) {
		int value = 0;
		for (int i = 0; i < word.length();) {
			int count = 0;
			for (int j = i; j < word.length(); j++) {
				if (word.charAt(j) != word.charAt(i)) {
					break;
				}
				count++;
			}
			if (count > 2) {
				// They are no double letters
				value += count;
			}
			i += count;
		}
		return value;
	}

	// Replaces occurrence of a repeated letter (more than twice) in the word
	// with only two letters.
	private String replaceRepeatedLetter(String word) {
		for (int i = 0; i < word.length();) {
			int count = 0;
			for (int j = i; j < word.length(); j++) {
				if (word.charAt(j) == word.charAt(i)) {
					count++;
					if (count > 2) {
						String temp = word.substring(0, j) + ':';
						if (j + 1 < word.length()) {
							temp += word.substring(j + 1);
						}
						word = temp;
					}
				} else {
					break;
				}
			}
			i += count;
		}
		word = word.replace(":", "");
		return word;
	}

	// Gets exclamation value according to its weight
	private double getExclamationValue(double EXCLAMATION, String text) {

		String regex; // initially 10 exclamation points or question marks
		Integer marksNumber = 10;
		while (marksNumber > 0) {
			regex = ".*[?!]{" + marksNumber.toString() + ",}.*";
			if (Pattern.matches(regex, text)) {
				// There are exclamation points or question marks in the text
				break;
			}
			marksNumber--;
		}

		double rate = marksNumber * 10;

		return (EXCLAMATION / 100) * rate;
	}

	// Returns the number of words in the text
	private int getDifferentWordsNumber(String text) {
		
		// Replaces punctuation and more than one blank space with one blank space
		text = text.replaceAll("[ \\p{Punct}]", " ");
		text = text.replaceAll(" {2,}", " ");
		
		// Obtains words from the text
		String[] words = text.split(" ");

		// For each word:
		// 1) Deletes punctuation and spaces;
		// 2) Replaces all the letters that occur more than twice with two
		// occurrences of the letter
		for (int i = 0; i < words.length; i++) {
			words[i] = words[i].replaceAll("[ \\p{Punct}]", "");
			words[i] = replaceRepeatedLetter(words[i].toLowerCase()); //returns a lower case string, so all words are lower case
		}

		List<String> wordsList = Arrays.asList(words);
		Set<String> wordsSet = new HashSet<String>(wordsList);

		// Returns how many different words are in the text
		return wordsSet.size();
	}

	// Gets low_different_words value according to its weight
	private double getLowDifferentWordsValue(double LOW_DIFFERENT_WORDS,
			String text) {

		// Counts how many different words are in the text
		int differentWords = getDifferentWordsNumber(text);

		// Calculates rate
		double rate;
		switch (differentWords) {
		case 1:
			rate = 100;
			break;
		case 2:
			rate = 96.6;
			break;
		case 3:
			rate = 93.3;
			break;
		case 4:
			rate = 90;
			break;
		default:
			rate = 90 - (22.5 * (differentWords - 4));
			break;
		}
		if (rate < 0)
			rate = 0;

		// Calculates returned value
		return (LOW_DIFFERENT_WORDS / 100) * rate;
	}

	// Gets high_different_words value according to its weight
	private double getHighDifferentWordsValue(double HIGH_DIFFERENT_WORDS,
			String text) {

		// Counts how many different words are in the text
		int differentWords = getDifferentWordsNumber(text);

		// Calculates rate
		double rate = 0;
		if (differentWords > 14) {
			rate = (12.5 * (differentWords - 14));
		}
		if (rate > 100) {
			rate = 100;
		}

		// Calculates returned value
		return (HIGH_DIFFERENT_WORDS / 100) * rate;
	}

	// Gets energy index from text
	public double getEnergyIndex(String text) {
		final double UPPER_CASE = 0.90;
		final double SAME_WORD = 0.20;
		final double MULTIPLIED_LETTER = 0.35;
		final double EXCLAMATION = 0.30;
		final double LOW_DIFFERENT_WORDS = 0.15;
		final double HIGH_DIFFERENT_WORDS = -0.10;
		
		return getUppercaseValue(UPPER_CASE, text)
				+ getSameWordValue(SAME_WORD, text)
				+ getMultipliedLetterValue(MULTIPLIED_LETTER, text)
				+ getExclamationValue(EXCLAMATION, text)
				+ getLowDifferentWordsValue(LOW_DIFFERENT_WORDS, text)
				+ getHighDifferentWordsValue(HIGH_DIFFERENT_WORDS, text);
	}
}
