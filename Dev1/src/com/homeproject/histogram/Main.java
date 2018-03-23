package com.homeproject.histogram;

public class Main {

	public static void main (String[] args) {
		String inputPath = "input.txt", outputPath = "output.txt";
		//default filepaths for optional arguments
		if(args.length >= 1 ) {
			inputPath = args[0];
		}
		if(args.length > 1) {
			outputPath = args[1];
		}

		//read the Filepath argument into the DAO to get file contents in String form
		String fileContents = DAO.readInFile(inputPath);
		//A string here works for small data sets, but larger ones will need a different implementation (probably batch load capability)

		//Read contents into HistogramMaker for parsing and printing
		HistogramMaker.make(fileContents, outputPath);
	}
}
