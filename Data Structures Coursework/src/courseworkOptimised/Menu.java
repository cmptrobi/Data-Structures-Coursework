package courseworkOptimised;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Menu {

	public static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) throws FileNotFoundException {
		HashMap<FilmData, String> Film = FilmData.readFile("SampleDataset-FilmTitle.csv");
		HashMap<personData, String> person = personData.readFile("SampleDataset-Person.csv");

		String Choice = " ";

		System.out.println("Film Data Menu");

		System.out.println();

		do {
			System.out.println("Main Menu");
			System.out.println("1. List of regions.");
			System.out.println("2. Films from specific region.");
			System.out.println("3. Partial film title search.");
			System.out.println("4. Film title and category for specified person.");
			System.out.println("5. Names and category of people who worked on specified film.");
			System.out.println("6. Person with highest number of credits and associated films");
			System.out.println("7. Person with highest number of credits from specified category and associated films");
			System.out.println("Q - Quit");
			System.out.print("Select : ");

			Choice = scan.next().toUpperCase();

			switch (Choice) {
			case "1": {
				regionList(Film);
				break;
			}
			case "2": {
				specifiedRegion(Film);
				break;
			}
			case "3": {
				titleSearch(Film);
				break;
			}
			case "4": {
				personSearch(person, Film);
				break;
			}
			case "5": {
				filmSearch(Film, person);
				break;
			}
			case "6": {
				highCredFilm(person, Film);
				break;
			}
			case "7": {

				break;
			}

			}
		} while (!Choice.equals("Q"));

		scan.close();
	}
	

	public static void regionList(HashMap<FilmData, String> film) {

		System.out.println("Here are all the regions which have film data");

		Map<regionType, Integer> filmCount = new HashMap<>();

		for (FilmData f : film) {
			int occ = 0;
			if (filmCount.containsKey(f.region))
				occ = filmCount.get(f.region);
			occ++;
			filmCount.put(f.region, occ);
		}

		for (regionType t : filmCount.keySet()) {
			System.out.println(t + " -> " + filmCount.get(t) + " occurrences");
		}

	}

	public static void specifiedRegion(HashMap<FilmData, String> film) {

		System.out.println("Which region would you like to get film titles from: ");
		String specRegion = scan.next();

		if (f.region.equals(regionType.getFrom(specRegion))) {
			System.out.println(f.title.toString());
		}

	}

	public static void titleSearch(HashMap<FilmData, String> film) {

		System.out.println("Which film title would you like to search for: ");
		String partTitle = scan.next();

		
			if (FilmData.containsKey(partTitle)) {
				System.out.print(FilmData.get(film.title.toString());
				System.out.print(", ");
				System.out.println(film.region.toString());
			
		}
	}

	public static void personSearch(HashMap<personData, String> person, HashMap<FilmData, String> film) {

		ArrayList<String> search = new ArrayList<>();

		System.out.println("What is the ID of the person who you would like to search for: ");
		String personID = scan.next();

		if (film.titleId.contains(personID)) {
			search.add(film.title.toString());
			if (person.tconst.contains(personID)) {
				System.out.print(person.nconst.toString());
				System.out.print(", ");
				System.out.print(person.category.toString());
				System.out.print(", ");
				System.out.print(search);
				System.out.println(", ");
			}
		}
	}

	public static void filmSearch(HashMap<FilmData, String> film, HashMap<personData, String> person) {
		String result = null;

		System.out.println("Which film title would you like to search for: ");
		String fullTitle = scan.next();

		if (film.title.equals(fullTitle)) {
			result = film.titleId.toString();
			if (person.tconst.equals(result)) {
				System.out.print(person.nconst.toString());
				System.out.print(", ");
				System.out.print(person.category.toString());
				System.out.println(", ");

			}
		}

	}

	public static void highCredFilm(HashMap<personData, String> person, HashMap<FilmData, String> film) {
		String result = null;
		Map<String, Integer> highCred = new HashMap<>();
		Map.Entry<String, Integer> mostRepeated = null;

		System.out.println("Here is the person with the highest amount of credits: ");

		int occ = 0;
		if (highCred.containsKey(film.titleId))
			occ = highCred.get(film.titleId);
		occ++;
		highCred.put(film.titleId, occ);

		for (Map.Entry<String, Integer> e : highCred.entrySet()) {
			if (mostRepeated == null || mostRepeated.getValue() < e.getValue())
				mostRepeated = e;
			if (mostRepeated != null) {

				if (person.tconst.equals(mostRepeated)) {
					System.out.print(person.tconst.toString());

				}
			}
		}
	}

	
	}
}
