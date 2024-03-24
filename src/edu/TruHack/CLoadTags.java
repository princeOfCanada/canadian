package edu.TruHack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CLoadTags {
	ArrayList<String> elements;

	public CLoadTags() {
		elements = new ArrayList<>();

	}

	ArrayList<String> getReadItems() {
		return elements;
	}

	// This function loads all the quotes from a file
	public void loadQuotesFromFile() {

		// I consulted the following example
		// https://stackoverflow.com/questions/13185727/reading-a-txt-file-using-scanner-class-in-java

		final String tagFileName = "tags.txt";

		try {
			// Construct the Scanner object for reading
			File inputFile = new File(tagFileName);
			Scanner in = new Scanner(inputFile);

			// Read the input and write the output
			while (in.hasNextLine()) {
				String line = in.nextLine();
				elements.add(line);
			}

			in.close();

		} catch (IOException exception) {
			System.out.println("Error opening the " + tagFileName + " file.");
			System.out.println("This file should be in the project path.");
		}

	}
}
