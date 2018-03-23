package com.homeproject.histogram;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class HistogramMaker {

	public static void make(String fileContents, String outputPath) {
		String[] formattedStrings = formatString(fileContents);
		List<Entry<String, Integer>> sortedWords = sortWords(countWords(formattedStrings));
		printHistogramToFile(sortedWords, outputPath);		
	}

	private static String[] formatString(String fileContents) {
		//change all letters to lower case, then split along common non-letter characters
		//this implementation will not handle non-ascii character sets
		String[] words = fileContents.toLowerCase().replaceAll("[^a-z]",  " ").split(" ");

		return words;
	}



	private static Map<String, Integer> countWords(String[] words) {
		HashMap<String, Integer> countedWords = new HashMap<String, Integer>();

		for(String word: words) {
			//check for initial word entry
			if(!countedWords.containsKey(word)) {
				countedWords.put(word, 1);
			}
			else {
				//if word already exists in the map replace its old value with an incremented value
				countedWords.replace(word, countedWords.get(word), countedWords.get(word) + 1);
			}			
		}
		//remove null character from count
		if(countedWords.containsKey("")) {
			countedWords.remove("");
		}		

		return countedWords;
	}



	private static List<Entry<String, Integer>> sortWords(Map<String, Integer> wordCount){
		LinkedList<Entry<String, Integer>> sortedWords = new LinkedList<Entry<String, Integer>>();
		//copy counted words (as entry word/count pairs) into a LinkedList for sorting
		sortedWords.addAll(wordCount.entrySet());

		//Begin sorting the pre-counted words, luckily Java 8 has this built in for collections
		sortedWords.sort(new Comparator<Entry<String, Integer>>() {
			@Override		
			//define a comparator for the .sort to use, in this case I want the values in desc order
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2){
				int result = 0;
				if(o1.getValue() < o2.getValue()) {
					result =  1;
				}
				else if(o1.getValue() > o2.getValue()) {
					result = -1;
				}

				return result;
			}     
		});		
		return sortedWords;		
	}



	private static void printHistogramToFile(List<Entry<String, Integer>> sortedWords, String outputPath) {
		//find the longest word for padding purposes in the final printout
		int longestWord = findLongestWord(sortedWords);
		//path for the desired output file
		Path path = Paths.get(outputPath);


		try (BufferedWriter writer = Files.newBufferedWriter(path)){

			for(int i = 0; i < sortedWords.size(); i++) {

				int currentValue = sortedWords.get(i).getValue();
				int currentKeyLength = sortedWords.get(i).getKey().length();

				//check if the current word is the longest word from the file, add padding as needed
				if(currentKeyLength < longestWord) {
					for(int j = 0; j < (longestWord - currentKeyLength); j++) {
						writer.write(" ");
					}
				}
				//write the word and add the pipe
				writer.write(sortedWords.get(i).getKey() + " | ");

				//add the requisite ='s matching word frequency
				for(int j = 0; j < currentValue; j++){
					writer.write("=");
				}
				//add spacing, parentheses, and the numeral frequency
				writer.write(" (" + ((Integer)currentValue).toString() + ")\n");
			}


		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	private static int findLongestWord(List<Entry<String, Integer>> sortedWords) {
		int currentMaxLength = 0;

		//Loop through the array of string to find the longest one
		for(int i = 0; i < sortedWords.size(); i++) {
			int currentKeyLength = sortedWords.get(i).getKey().length();

			if(currentKeyLength > currentMaxLength) {
				currentMaxLength = currentKeyLength;
			}
		}
		return currentMaxLength;		
	}
}
