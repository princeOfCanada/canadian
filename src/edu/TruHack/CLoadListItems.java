package edu.TruHack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CLoadListItems {
	ArrayList<String> elements;

	public CLoadListItems() {
		elements = new ArrayList<>();

	}

	ArrayList<String> getReadListItems() {
		return elements;
	}


	// This function loads all the quotes from a file
	public void loadListItemsFromFile(String fileListName) {

		// I consulted the following example
		// https://stackoverflow.com/questions/13185727/reading-a-txt-file-using-scanner-class-in-java
		try {
			// Construct the Scanner object for reading
			File inputFile = new File(fileListName);
			Scanner in = new Scanner(inputFile);

			// Read the input and write the output
			while (in.hasNextLine()) {
				String line = in.nextLine();
				int index= line.indexOf('#');
				line = line.substring(0, index);
				elements.add(line);
			}

			in.close();

		} catch (IOException exception) {
			System.out.println("Error opening the " + fileListName + " file.");
			System.out.println("This file should be in the project path.");
		}

	}
}
