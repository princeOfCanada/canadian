package edu.TruHack;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class CMain {

	final int SIZE = 9;

	// to input a new idea or text
	JTextArea enterText;
	// command button
	JButton myMainButton;

	JList<String> myTagList; // all tag items are stored here
	JList<String> myItemsList; // all todo items are added here
	CListManager listControl;

	JFrame todoMainWindowApp; // main window object
	// The JPanel holds the buttons
	JPanel leftPanel = new JPanel();
	JMultiLineLabel mySelectedTextInsideDialogWindow;
	JMultiLineLabel myInspiringText;

	// The first function that is called for the application object
	// here we create the GUI
	// i consulted the following web articl:
	// https://www.guru99.com/java-swing-gui.html
	public CMain(String title) {
		final String MESSAGE_EMPTY = "Click list item to see detailed description. Double click a list item on the right to remove it.";
		final int HEIGHT = 700;
		todoMainWindowApp = new JFrame(title);
		mySelectedTextInsideDialogWindow = new JMultiLineLabel(MESSAGE_EMPTY);


		// load inspiring quotes
		CLoadInspiringQuotes myInspiringQuotes = new CLoadInspiringQuotes();
		// leftPanel contents

		leftPanel.setLayout(new FlowLayout());
		leftPanel.setPreferredSize(new Dimension(300, HEIGHT));
		CTagManager tagControl = new CTagManager();
		myTagList = tagControl.getGUIList();
		myTagList.setFont(new Font("SansSerif", Font.PLAIN, 16));
		myTagList.setPreferredSize(new Dimension(280, HEIGHT - 230));

		// I figured out this problem by consulting the following article
		// https://stackoverflow.com/questions/966625/how-can-i-get-the-text-from-a-component-in-a-jlist
		myTagList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JList list = (JList) evt.getSource();
				if (evt.getClickCount() == 2) {
					// Double-click detected
					int index = list.locationToIndex(evt.getPoint());
					String selectedTagName = "" + list.getModel().getElementAt(index);
					System.out.println(selectedTagName);
					// make the list control load the list content from the selected tag
					// I have to make listControl global
					listControl.reloadList(selectedTagName);
					// reload the list from the ArrayList
					myItemsList = listControl.getGUIList();
					// debugging purpose
					System.out.println(myItemsList.getModel().getSize());
					myItemsList.validate();

					// reset the description text
					mySelectedTextInsideDialogWindow.setText(MESSAGE_EMPTY);

					// get a random inspiring text
					myInspiringText.setText(myInspiringQuotes.getRandomQuote());

				}
			}
		});
		
		leftPanel.add(myTagList); // add the tag list

		mySelectedTextInsideDialogWindow.setPreferredSize(new Dimension(280, 220));
		leftPanel.add(mySelectedTextInsideDialogWindow); // add the description label

		final int CENTER_WIDTH = 620;

		// center panel contents
		JPanel centerPanel = new JPanel();
		centerPanel.setPreferredSize(new Dimension(CENTER_WIDTH, HEIGHT));
		myInspiringText = new JMultiLineLabel("Something inspiring");
		myInspiringText.setPreferredSize(new Dimension(CENTER_WIDTH, 60));
		myInspiringText.setFont(new Font("SansSerif", Font.BOLD, 14));
		

		myInspiringText.setText(myInspiringQuotes.getRandomQuote());
		centerPanel.setLayout(new FlowLayout());
		centerPanel.add(myInspiringText);

		// The main list items area
		String firstTagName = tagControl.getTopTagName();
		listControl = new CListManager(firstTagName);
		myItemsList = listControl.getGUIList();
		myItemsList.setFont(new Font("SansSerif", Font.PLAIN, 15));
		myItemsList.setPreferredSize(new Dimension(CENTER_WIDTH - 20, HEIGHT - 115));
		JScrollPane myItemListScrollPane = new JScrollPane(myItemsList);
		myItemListScrollPane.setPreferredSize(new Dimension(CENTER_WIDTH, HEIGHT - 115));

		// what happens when we interact with the list items
		myItemsList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {

				if (evt.getClickCount() == 2) { // 2 clicks or double clicks
					listControl.removeSelectedElement();
					// reset the description text
					mySelectedTextInsideDialogWindow.setText(MESSAGE_EMPTY);

				}

				else { // single click event

				JList list = (JList) evt.getSource();
				int listSize = list.getModel().getSize();
					// single click event
					int index = list.locationToIndex(evt.getPoint());
				// is the index valid, probably do not need to check this
				if (index >= 0 && index < listSize) {
					String selectedTagName = "" + list.getModel().getElementAt(index);
					// list.setToolTipText(selectedTagName);
					// System.out.println(selectedTagName);
					mySelectedTextInsideDialogWindow.setText(selectedTagName);
					
				}
				}
			}
		});

		centerPanel.add(myItemListScrollPane);


		// textBoxPanel side panel contents
		JPanel textBoxPanel = new JPanel();
		textBoxPanel.setLayout(new FlowLayout());
		enterText = new JTextArea();
		// enterText.setPreferredSize(new Dimension(CENTER_WIDTH - 60, 40));
		enterText.setFont(new Font("SansSerif", Font.PLAIN, 14));
		JScrollPane myTextBoxScrollPane = new JScrollPane(enterText);

		// I am at lost what width to choose
		// I have run this program already 50 times to correct the dimension/position

		myTextBoxScrollPane.setPreferredSize(new Dimension(CENTER_WIDTH - 60, 35));
		textBoxPanel.add(myTextBoxScrollPane);
		
		myMainButton = new JButton("Add");
		myMainButton.setPreferredSize(new Dimension(54, 40));
		// used some ideas presented here in this website
		// https://stackoverflow.com/questions/21879243/how-to-create-on-click-event-for-buttons-in-swing
		myMainButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {

				if (enterText.getText().length() < 10)
					return; // do nothing; too small text

				String addedTagName = tagControl.getTagNameFromText(enterText.getText());

				if (addedTagName.length() == 0) { // no tag has been added
					// add the element in the arrayList
					listControl.addElement(enterText.getText());
					enterText.setText("");
					// reload the list from the ArrayList
					myItemsList = listControl.getGUIList();
					// debugging purpose
					// System.out.println(myItemsList.getModel().getSize());
					myItemsList.validate();

				}
				// this is an existing tag
				else if (tagControl.checkNewTagName(enterText.getText()) == false) {
					
					// this is the current tag, add the list item in the current tag
					if (listControl.isTagSelected((addedTagName)) == true) {
							// add the element in the arrayList
							listControl.addElement(enterText.getText());
							enterText.setText("");
							// reload the list from the ArrayList
							myItemsList = listControl.getGUIList();
							// debugging purpose
							// System.out.println(myItemsList.getModel().getSize());
							myItemsList.validate();

					} else // different existing tag has been specified
					{
						// reload the tag list
						myTagList = tagControl.getGUIList();
						int selectedTagIndex = tagControl.getSelectedTagIndex(addedTagName);
						if (selectedTagIndex != -1)
							myTagList.setSelectedIndex(selectedTagIndex);

						listControl.reloadList(addedTagName);
						// reload the list from the ArrayList
						myItemsList = listControl.getGUIList();
						// debugging purpose
						System.out.println(myItemsList.getModel().getSize());
						myItemsList.validate();

						// reset the description text
						mySelectedTextInsideDialogWindow.setText(MESSAGE_EMPTY);

						// now add the element in this tag list
						listControl.addElement(enterText.getText());
						enterText.setText("");
						// reload the list from the ArrayList
						myItemsList = listControl.getGUIList();
						// debugging purpose
						// System.out.println(myItemsList.getModel().getSize());
						myItemsList.validate();

					}

				}
				else { // this is a new tag that is not present in the list
					myTagList = tagControl.getGUIList();
					int selectedTagIndex = tagControl.getSelectedTagIndex(addedTagName);
					if (selectedTagIndex != -1)
						myTagList.setSelectedIndex(selectedTagIndex);

					listControl.reloadANewFileList(addedTagName);
					// reload the list from the ArrayList
					myItemsList = listControl.getGUIList();

					// reset the description text
					mySelectedTextInsideDialogWindow.setText(MESSAGE_EMPTY);

					// now add the element in this tag list
					listControl.addElement(enterText.getText());
					enterText.setText("");
					// reload the list from the ArrayList
					myItemsList = listControl.getGUIList();
					// debugging purpose
					// System.out.println(myItemsList.getModel().getSize());
					myItemsList.validate();
				}
          }
		});

		textBoxPanel.add(myMainButton); // the textbox and the add button

		centerPanel.add(textBoxPanel);

		// I learned the layout manager from the following website
		// https://www.javatpoint.com/java-layout-manager
		// adding all the panels to the main window
		todoMainWindowApp.setLayout(new BorderLayout());
		todoMainWindowApp.add(leftPanel, BorderLayout.WEST);

		todoMainWindowApp.add(centerPanel, BorderLayout.CENTER);

		todoMainWindowApp.setSize(980, HEIGHT + 40);
		todoMainWindowApp.setVisible(true);
		todoMainWindowApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	// application program
	public static void main(String[] args) {
		// The main program is created here
		CMain toDoManagerWindow = new CMain("Motivational ToDo Manager Well-being Theme");
	}

}


