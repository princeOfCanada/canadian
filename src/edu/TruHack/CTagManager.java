package edu.TruHack;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

public class CTagManager {

	ArrayList<String> elements;
	JList<String> myList;

	public CTagManager() {
		myList = new JList<>();

		elements = new ArrayList<>();
		addAllElements();
	}

	// returns the first tag name loaded from the file.
	public String getTopTagName() {
		return elements.get(0);
	}

	public void addAllElements() {
		CLoadTags allMyTags = new CLoadTags();
		allMyTags.loadQuotesFromFile();
		elements.clear();
		elements = allMyTags.getReadItems();
	}

	public void addElement(String elem) {
		elements.add(elem);
		saveTagItemsToFile();
	}

	// gets the added tag name
	String getTagNameFromText(String entryText) {
		int index = entryText.indexOf('#');
		String tagName = "";
		if (index >= 0)
			tagName = entryText.substring(index);
		return tagName;
	}

	// returns the index of the tagName in the List
	int getSelectedTagIndex(String tagName) {
		for (int i = 0; i < elements.size(); i++) {
			if (tagName.equalsIgnoreCase(elements.get(i)))
				return i;
		}
		return -1; // does not exist
	}

	// checks whether the added tag is new
	boolean checkNewTagName(String entryText) {
		int index = entryText.indexOf('#');
		String tagName = entryText.substring(index);
		boolean flag = false;
		for (int i = 0; i < elements.size(); i++) {
			if (tagName.equalsIgnoreCase(elements.get(i)))
				flag = true;
		}
		
		// Having problem with my thoughts
		// it might be a logical problem
		if (flag == true) { // tag already exist
			return false; // this is not a new tag
		} else
		{ // this is a new tag
			addElement(tagName);
			return true;
		}
	}

	// save all the tags to the tags file (when new tag is added)
	public void saveTagItemsToFile() {
		final String tagFileName = "tags.txt";
		try {

			// I consulted the following web article for the following
			// https://www.programiz.com/java-programming/printwriter
			// Construct the Scanner object for reading
			PrintWriter out = new PrintWriter(tagFileName);

			// Read the input and write the output
			for (int i = 0; i < elements.size(); i++) {
				String line = elements.get(i);
				// System.out.println(line);
				out.println(line);
		}

			out.close();
		} catch (IOException exception) {
			System.out.println("Error writing to " + tagFileName + " file.");
			System.out.println("This file should be in the project path.");
	}
	}

	// create a JList with the tag items
	JList<String> getGUIList() {
		// first empty the list
		myList.removeAll();

		// I consulted the following web article
		// https://www.tutorialspoint.com/swingexamples/creating_single_interval_listbox.htm

		// # add the elements from the arraylist
		DefaultListModel<String> listElements = new DefaultListModel<>();

		for (int i = 0; i < elements.size(); i++) {
			listElements.addElement(elements.get(i));

		}
		// create the JList with the elements
		// myList = new JList<String>(listElements);
		myList.setModel(listElements);
		myList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		myList.setLayoutOrientation(JList.VERTICAL); // one element in one row

		// make the first item selected
		myList.setSelectedIndex(0);

		return myList;
	}

}
