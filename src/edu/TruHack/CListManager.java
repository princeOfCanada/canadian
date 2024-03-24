package edu.TruHack;


import java.awt.Color;
import java.awt.Component;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

public class CListManager {

	ArrayList<String> elements;
	JList<String> myList;
	String currentTagName;

	public CListManager(String firstTagName) {
		myList = new JList<>();

		elements = new ArrayList<>();
		addAllElements(firstTagName);

		currentTagName = firstTagName;
	}

	// is the tag same as the current on
	boolean isTagSelected(String tagName) {
		if (tagName.equalsIgnoreCase(currentTagName))
			return true;
		else
			return false;
	}

	// loads the file content from the tagName
	public void reloadList(String tagName) {
		// reload the items again
		addAllElements(tagName);
		currentTagName = tagName;
	}

	// this tagName is new, create the file entry first
	public void reloadANewFileList(String tagName) {
		currentTagName = tagName;
		String fileNameFromTag = tagName.substring(1) + ".txt";
		try {
		PrintWriter out = new PrintWriter(fileNameFromTag);
		out.close();
		}
		catch (IOException exception) {
			System.out.println("Error writing to " + fileNameFromTag + " file.");
			System.out.println("This file should be in the project path.");
		}
		addAllElements(tagName);

	}

	// read the file for this tag again, and recreate the list and arrayList
	public void addAllElements(String tagName) {
		currentTagName = tagName;
		String fileNameFromTag = tagName.substring(1) + ".txt";
		CLoadListItems allMyListItems = new CLoadListItems();
		allMyListItems.loadListItemsFromFile(fileNameFromTag);
		elements.clear();
		elements = allMyListItems.getReadListItems();
	}

	// add one element adn immediately save it to file
	public void addElement(String elem) {
		String clearedTxt = "";
		for (int i = 0; i < elem.length(); i++) {
			// Bug: we can't copy the newline text
			// it will create another entry in the file
			if (elem.charAt(i) != '\n' && elem.charAt(i) != '\r')
				clearedTxt = clearedTxt + elem.charAt(i);
		}

		// remove the # from the text
		String line = clearedTxt;
		int index = line.indexOf('#');
		if (index > 0)
		line = line.substring(0, index);

		elements.add(line);
		saveListItemsToFile();
	}

	// save all the contents to the file
	public void saveListItemsToFile() {
		String fileNameFromTag = "";
		try {
			fileNameFromTag = currentTagName.substring(1) + ".txt";

			// I consulted the following web article for the following
			// https://www.programiz.com/java-programming/printwriter
			// Construct the Scanner object for reading
			PrintWriter out = new PrintWriter(fileNameFromTag);

			// Read the input and write the output
			for (int i = 0; i < elements.size(); i++) {
				String line = elements.get(i) + " #" + this.currentTagName;
				// System.out.println(line);
				out.println(line);
			}

			out.close();
		} catch (IOException exception) {
			System.out.println("Error writing to " + fileNameFromTag + " file.");
			System.out.println("This file should be in the project path.");
		}
	}

	// removes the selected element on double click event
	public void removeSelectedElement() {
		// learned this idea from the following link
		// https://stackoverflow.com/questions/9402658/delete-selected-item-from-jlist

		DefaultListModel model = (DefaultListModel) myList.getModel();
		int selectedIndex = myList.getSelectedIndex();
		if (selectedIndex != -1) {
			String selectedItem = "" + model.get(selectedIndex);
			elements.remove(selectedItem); // remove selected item from ArrayList

			model.remove(selectedIndex); // remove it from the model
			// save the updated list to the file
			saveListItemsToFile(); // save the updated list to the file
		}


	}

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
		myList.setModel(listElements);
		// myList = new JList<String>(listElements);
		myList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		myList.setLayoutOrientation(JList.VERTICAL); // one element in one row

		// I wanted to use different color for even and odd items of the list
		// So I have used the following ideas
		// I got this idea from:
		// https://coderanch.com/t/641342/java/Changing-color-JList-item
		class MyListCellRenderer extends DefaultListCellRenderer {
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				// I got the colors from:
				// https://www.rapidtables.com/convert/color/hex-to-rgb.html
				Color evenColor = new Color(242, 243, 255);
				Color oddColor = new Color(232, 233, 235);
				if (index % 2 == 0) {
					c.setBackground(evenColor);
				} else {
					c.setBackground(oddColor);
				}
				if (isSelected) {
					setBackground(getBackground().darker());
				}

				return c;
			}
		}
		// Now the list can use the color render to have different background colors
		// https://coderanch.com/t/641342/java/Changing-color-JList-item

		myList.setCellRenderer(new MyListCellRenderer());
		return myList;
	}

}
