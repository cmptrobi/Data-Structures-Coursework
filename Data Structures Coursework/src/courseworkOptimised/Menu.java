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
		HashMap<FilmData, String> film = FilmData.readFile("SampleDataset-FilmTitle.csv");
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
				regionList(film);
				break;
			}
			case "2": {
				specifiedRegion(film);
				break;
			}
			case "3": {
				titleSearch(film);
				break;
			}
			case "4": {
				personSearch(person, film);
				break;
			}
			case "5": {
				filmSearch(film, person);
				break;
			}
			case "6": {
				highCredFilm(person, film);
				break;
			}
			case "7": {

				break;
			}

			}
		} while (!Choice.equals("Q"));

		scan.close();
	}

	//Feature E: Finds the highest credit Film
	private static void highCredFilm(HashMap<personData, String> person, HashMap<FilmData, String> film) {
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

	//Feature D b:  Shows contributions to a specific film
	private static void filmSearch(HashMap<FilmData, String> film, HashMap<personData, String> person) {
		String result = null;

		System.out.println("Which film title would you like to search for: ");
		String fullTitle = scan.next();

		if (film.title.containsKey(fullTitle)) {
			result = film.titleId.toString();
			if (person.tconst.equals(result)) {
				System.out.print(person.nconst.toString());
				System.out.print(", ");
				System.out.print(person.category.toString());
				System.out.println(", ");

			}
		}

	}
	
	//Feature D a: Searches for specific persons films
	private static void personSearch(HashMap<personData, String> person, HashMap<FilmData, String> film) {
		ArrayList<String> search = new ArrayList<>();

		System.out.println("What is the ID of the person who you would like to search for: ");
		String personID = scan.next();

		if (film.titleId.containsKey(personID)) {
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

	//Feature C: Finds potential films from a partial title
	private static void titleSearch(HashMap<FilmData, String> film) {
		System.out.println("Which film title would you like to search for: ");
		String partTitle = scan.next();

		
			if (film.title.containsKey(partTitle)) {
				System.out.print(FilmData.get(film.title.toString());
				System.out.print(", ");
				System.out.println(film.region.toString());
			
	}
	}

	//Feature B: Shows specific films from a region
	private static void specifiedRegion(HashMap<FilmData, String> film) {
		System.out.println("Which region would you like to get film titles from: ");
		String specRegion = scan.next();		
		
		if (film.region.containsKey(regionType.getFrom(specRegion))) {
			System.out.println(film.title.toString());
		}

	}

	//Feature A: Shows all the regions that data is from
	private static void regionList(HashMap<FilmData, String> film) {

		System.out.println("Here are all the regions which have film data");

		Map<regionType, Integer> filmCount = new HashMap<>();

		for (regionType t : filmCount.keySet()) {
			System.out.println(t + " -> " + filmCount.get(t) + " occurrences");

		}
	}

	public enum regionType {
		NA("\\N"), AR("AR"), AT("AT"), AU("AU"), BE("BE"), BR("BR"), CO("CO"), CSHH("CSHH"), CZ("CZ"), DE("DE"), DK(
				"DK"), ES("ES"), FI("FI"), FR("FR"), GB("GB"), GR("GR"), HU("HU"), IN("IN"), IT("IT"), JP("JP"), MX(
						"MX"), NL("NL"), NO("NO"), PL("PL"), PT("PT"), RO("RO"), RS("RS"), RU("RU"), SE("SE"), SI(
								"SI"), TR("TR"), US("US"), UY("UY"), VE("VE"), XEU("XEU"), XWW("XWW"), XYU("XYU");

		private final String str;

		private regionType(String aStr) {
			this.str = aStr;
		}

		public String toString() {
			return this.str;
		}

		public static regionType getFrom(String aStr) {
			for (regionType t : regionType.values())
				if (t.str.equals(aStr))
					return t;
			throw new IllegalArgumentException("Could not find a region :" + aStr);
		}
	}
}
