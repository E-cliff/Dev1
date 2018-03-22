package com.homeproject.histogram;

import java.util.List;
import java.util.Map;

public class Main {

	public static void main (String[] args) {
	
	//read the Filepath argument into the DAO to get file contents in String form
	String fileContents = DAO.readInFile(args[0]);
	
	//Parse contents to get histogram in Map form
	HistogramMaker.maker(fileContents);
	
	}
}
