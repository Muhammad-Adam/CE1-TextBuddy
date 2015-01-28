import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;

/**
  * Code written by: Muhammad Adam
  * Student ID: A0121813U

  * This class is used to create a text file where user can add, delete, clear and display text.
  * User can store as many lines of text as he or she wants.
  * Limited to commands: "add", "clear", "delete", "display" and "exit". NO OTHER COMMANDS OR SHORTCUT AVAILABLE
  * If user were to add an exact copy of text, the class WOULD NOT ignore the command and just execute it.
  * The command format is given by the example interaction below:
  
Welcome to TextBuddy. mytextfile.txt is ready for use
command: add rehearse for oral presentation
added to mytextfile.txt: "rehearse for oral presentation"
command: display
1. rehearse for oral presentation
command: add remember to buy groceries for mother
added to mytextfile.txt: "remember to buy groceries for mother"
command: display
1. rehearse for oral presentation
2. remember to buy groceries for mother
command: delete 1
deleted from mytextfile.txt: "rehearse for oral presentation"
command: display
1. remember to buy groceries for mother
command: clear
all content deleted from mytextfile.txt
command: display
mytextfile.txt is empty
command: exit

**/

public class TextBuddy {
	private static boolean isRunning = true;
	private static Scanner input = new Scanner(System.in);
	
	private static void printWelcomeMessage(String string) {
		System.out.println("Welcome to TextBuddy. " + string + " is ready for use");
	}
	
	// Upon starting up the application, program will create a new text file with the name based on user input
	private static void createNewWriter(String file) {
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(file, true));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String promptForCommand() {
		System.out.print("command: ");
		return input.nextLine();
	}
	
	// Program will append the specific line of text to the file given
	private static void addLineToFile(String file, String lineOfText ){
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(file, true));
			writer.println(lineOfText);
			System.out.println("added to " + file + ": " + "\"" + lineOfText + "\"");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Program will print line by line to the console, appending bulletpoints before each line
	private static void printLineOnScreen(String file) {
		try {
			File content = new File(file);
			Scanner textFile = new Scanner(content);
			if (!textFile.hasNextLine()) {
				System.out.println(file + " is empty");
			} else {
				int bulletpoint = 1;
				while (textFile.hasNextLine()) {
					System.out.println("" + bulletpoint + ". " + textFile.nextLine());
					bulletpoint++;
				}
			}
			textFile.close();
		} catch (FileNotFoundException e) {
			System.err.println("File not found in the working directory");
		}
	}
	
	// Program will delete all the contents of the file
	private static void clearFile(String file) {
		try {
			PrintWriter writer = new PrintWriter(file);
			writer.close();
			System.out.println("all content deleted from " + file);
		} catch (FileNotFoundException e) {
			System.out.println("File not found in working directory");
		}
	}
	
	// Program will store all the lines in an array
	private static ArrayList<String> storeInArrayList(String file) {
		ArrayList<String> listOfText = new ArrayList<String>();
		try {
			File content = new File(file);
			Scanner textFile = new Scanner(content);
		
			while (textFile.hasNextLine()) {
				listOfText.add(textFile.nextLine());
			}
			textFile.close();
		} catch (FileNotFoundException e) {
			System.err.println("File not found in working directory...");
		}
		return listOfText;
	}
	
	// Program will delete the nth line of the file
	private static void deleteLineInFile(String file, String number) {
		try {
			ArrayList<String> allLines = storeInArrayList(file);
			int counter = 1;
			int index = Integer.parseInt(number);
			boolean isDeleted = false;
			PrintWriter writer = new PrintWriter(new FileWriter(file, false));
		
			for (String line: allLines) {
				if(counter != index){
				writer.println(line);
				counter++;
				} else {
					isDeleted = true;
					System.out.println("deleted from " + file + ": " + "\"" + line + "\"");
					counter++;
				}
			}
			if (!isDeleted) {
				System.out.println("Line number " + number + " does not exist...");
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Program will check what command was issued before calling out other functions
	private static void executeCommand(String file, String string) {
		String[] stringArr = string.split(" ", 2);
		String keyword = stringArr[0];
		
		if (keyword.equals("add")) {
			try {
				String actionWord = stringArr[1];
				addLineToFile(file, actionWord);
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Nothing to add...");
			}
		} else if (keyword.equals("exit")) {
			isRunning = false;
		} else if (keyword.equals("display")) {
			printLineOnScreen(file);
		} else if (keyword.equals("clear")) {
			clearFile(file);
		} else if (keyword.equals("delete")) {
			try {
				String actionWord = stringArr[1];
				deleteLineInFile(file, actionWord);
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Please provide the line to be deleted...");
			}
		} else {
			System.out.println("Unknown command given...");
		}
	}

    public static void main(String[] args) {
		printWelcomeMessage(args[0]);
		createNewWriter(args[0]);
		while (isRunning) {
			String command = promptForCommand();
			executeCommand(args[0], command);
		}	
    }
}