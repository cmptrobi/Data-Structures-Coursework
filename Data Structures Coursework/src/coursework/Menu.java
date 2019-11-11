package coursework;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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

				break;
			}
			case "7": {

				break;
			}

			}
		} while (!Choice.equals("Q"));

		scan.close();

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
			if (f.region.equals(regionType.getFrom(specRegion)))
				System.out.println(f.toCSVString());
		}

	}
	
	public static void titleSearch(List<FilmData> film) {
		
		System.out.println("Which film title would you like to search for: ");
		String partTitle = scan.next();
		
		for (FilmData f : film) {
			if (f.title.contains(partTitle))
				System.out.println(f.toCSVString());
		}
	}
	
	public static void personSearch(List<personData> person, List<FilmData> film) {
		
		System.out.println("What is the ID of the person who you would like to search for: ");
		String personID = scan.next();
		
		for (personData p : person) (FilmData f : film) {
			if (p.tconst.contains(personID))
				System.out.println(p.toCSVString());
		}
		for (FilmData f : film) {
			if (f.titleId.contains(personID))
				System.out.println(f.toCSVString());
		}
		
	}
	
	public static void filmSearch(List<FilmData> film, List<personData> person) {
		
		System.out.println("Which film title would you like to search for: ");
		String fullTitle = scan.next();
		
		for (FilmData f : film) {
			if (f.title.equals(fullTitle))
				System.out.println(f.toCSVString());
		}
	}

}
