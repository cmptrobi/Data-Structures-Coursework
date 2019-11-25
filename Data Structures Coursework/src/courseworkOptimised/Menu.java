package courseworkOptimised;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import coursework.FilmData;
import coursework.personData;
import coursework.Menu.regionType;

public class Menu {

	public static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) throws FileNotFoundException {
		ArrayList<FilmData> film = FilmData.readFile("SampleDataset-FilmTitle.csv");
		ArrayList<personData> person = personData.readFile("SampleDataset-Person.csv");

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

	public static void regionList(List<FilmData> film) {

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

	public static void specifiedRegion(List<FilmData> film) {

		System.out.println("Which region would you like to get film titles from: ");
		String specRegion = scan.next();

		for (FilmData f : film) {
			if (f.region.equals(regionType.getFrom(specRegion))) {
				System.out.println(f.title.toString());
			}
		}

	}

	public static void titleSearch(List<FilmData> film) {

		System.out.println("Which film title would you like to search for: ");
		String partTitle = scan.next();

		for (FilmData f : film) {
			if (f.title.contains(partTitle)) {
				System.out.print(f.title.toString());
				System.out.print(", ");
				System.out.println(f.region.toString());
			}
		}
	}

	public static void personSearch(List<personData> person, List<FilmData> film) {

		ArrayList<String> search = new ArrayList<>();

		System.out.println("What is the ID of the person who you would like to search for: ");
		String personID = scan.next();

		for (FilmData f : film) {
			if (f.titleId.contains(personID)) {
				search.add(f.title.toString());
				for (personData p : person) {
					if (p.tconst.contains(personID)) {
						System.out.print(p.nconst.toString());
						System.out.print(", ");
						System.out.print(p.category.toString());
						System.out.print(", ");
						System.out.print(search);
						System.out.println(", ");
					}
				}
			}
		}
	}

	public static void filmSearch(List<FilmData> film, List<personData> person) {
		String result = null;

		System.out.println("Which film title would you like to search for: ");
		String fullTitle = scan.next();

		for (FilmData f : film) {
			if (f.title.equals(fullTitle)) {
				result = f.titleId.toString();
				for (personData p : person) {
					if (p.tconst.equals(result)) {
						System.out.print(p.nconst.toString());
						System.out.print(", ");
						System.out.print(p.category.toString());
						System.out.println(", ");
					}
				}
			}
		}
	}

	public static void highCredFilm(List<personData> person, List<FilmData> film) {
		String result = null;
		Map<String, Integer> highCred = new HashMap<>();
		Map.Entry<String, Integer> mostRepeated = null;

		System.out.println("Here is the person with the highest amount of credits: ");

		for (FilmData f : film) {
			int occ = 0;
			if (highCred.containsKey(f.titleId))
				occ = highCred.get(f.titleId);
			occ++;
			highCred.put(f.titleId, occ);

			for (Map.Entry<String, Integer> e : highCred.entrySet()) {
				if (mostRepeated == null || mostRepeated.getValue() < e.getValue())
					mostRepeated = e;
				if (mostRepeated != null) {
					for (personData d : person) {
						if (d.tconst.equals(mostRepeated)) {
							System.out.print(d.tconst.toString());
						}
					}
				}
			}
		}
	}
}
