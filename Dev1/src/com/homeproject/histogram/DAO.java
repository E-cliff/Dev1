package com.homeproject.histogram;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DAO {
	//Data access object

	public static String readInFile(String path) {

		List<String> output = null;
		try {
			//this implementation works for small data sets, very large data sets will need more and different methods
			output = Files.readAllLines(Paths.get(path));
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
		return output.toString();
	}
}
