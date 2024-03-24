package edu.TruHack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class CLoadInspiringQuotes {
	ArrayList<String> elements;

	public CLoadInspiringQuotes() {
		elements = new ArrayList<>();
		loadQuotesFromFile();
	}

	// this function returns random String quote
	String getRandomQuote() {
		String quote = "\"It takes courage to grow up and become who you really are.\" — E.E. Cummings";
		
		if(elements.size() ==0) return quote;
		else
		{
			// generate a random value
			Random rand = new Random();
			int index = rand.nextInt(elements.size());
			// get the String quote at the index
			return elements.get(index);
		}
	}

	// This function loads all the quotes from a file
	public void loadQuotesFromFile() {

		// I consulted the following example
		// https://stackoverflow.com/questions/13185727/reading-a-txt-file-using-scanner-class-in-java

		final String quoteFileName = "quotes.txt";

		try {
			// Construct the Scanner object for reading
			File inputFile = new File(quoteFileName);
			Scanner in = new Scanner(inputFile);

			// Read the input and write the output
			while (in.hasNextLine()) {
				String line = in.nextLine();
				elements.add(line);
			}

			in.close();

		} catch (IOException exception) {
			System.out.println("Error opening the " + quoteFileName + " file.");
			System.out.println("This file should be in the project path.");
		}

	}
}
