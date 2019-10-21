package coursework;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

	public static void main(String[] args) throws FileNotFoundException {
		ArrayList<FilmData> film = readFile("SampleDataset-FilmTitle.csv");
		
		Scanner scan = new Scanner(System.in);
		
		String Choice = " ";

		System.out.println("--Film Data Menu--");

		System.out.println();

		do {
			System.out.println("Main Menu");
			System.out.println("1.");
			System.out.println("2.");
			System.out.println("3.");
			System.out.println("4.");
			System.out.println("5.");
			System.out.println("6.");
			System.out.println("Q - Quit");
			System.out.print("Select : ");

			Choice = scan.next().toUpperCase();

			switch (Choice) {
			case "1": {
				
				break;
			}
			case "2": {
				
				break;
			}
			case "3": {
				
				break;
			}
			case "4": {
				
				break;
			}
			case "5": {
				
				break;
			}
			case "6": {
				
				break;
			}

			}
		} while (!Choice.equals("Q"));

		scan.close();


	}

}
