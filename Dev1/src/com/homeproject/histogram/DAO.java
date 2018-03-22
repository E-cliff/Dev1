package com.homeproject.histogram;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DAO {
	//Data access object

	//
	public static String readInFile(String path) {


		List<String> output = null;
		try {
			output = Files.readAllLines(Paths.get(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output.toString();

	}
}
