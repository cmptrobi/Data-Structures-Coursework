package coursework;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class FilmData {

	public final String SEP = ",";
	public String titleId;
	public String ordering;
	public String title;
	public String region;
	public String language;
	public String types;
	public String attributes;
	public String isOriginalTitle;

	public FilmData(String csvString) {
		String[] csvParts = lineSplit(csvString, SEP);
		int idx = 0;
		titleId = csvParts[idx++];
		ordering = csvParts[idx++];
		title = csvParts[idx++];
		region = csvParts[idx++];
		language = csvParts[idx++];
		types = csvParts[idx++];
		attributes = csvParts[idx++];
		isOriginalTitle = csvParts[idx++];

	}

	public String[] lineSplit(String csvString, String s) {
		int index = 0;
		int qouteCount = 0;
		String[] parts = new String[8];
		Arrays.fill(parts, "");

		for (int i = 0; i < csvString.length(); i++) {
			if (csvString.charAt(i) != ',') {
				parts[index] += csvString.charAt(i);
			}
			if (csvString.charAt(i) == '"') {
				qouteCount++;
			}
			if (csvString.charAt(i) == ',' && qouteCount % 2 == 0)
				index++;
			if (csvString.charAt(i) == ',' && qouteCount % 2 == 1)
				parts[index] += csvString.charAt(i);
		}
		return parts;
	}

	public String toCSVString() {
		return titleId + SEP + ordering + SEP + title + SEP + region + SEP + language + SEP + types + SEP + attributes
				+ SEP + isOriginalTitle;
	}

	public static void main(String[] args) throws FileNotFoundException {
		ArrayList<FilmData> film = readFile("SampleDataset-FilmTitle.csv");

	}

	public static ArrayList<FilmData> readFile(String filename) throws FileNotFoundException {
		ArrayList<FilmData> films = new ArrayList<>();
		File csvFile = new File(filename);
		Scanner csvScan = new Scanner(csvFile);
		csvScan.nextLine(); // read header
		while (csvScan.hasNextLine()) {
			String line = csvScan.nextLine();
			FilmData film = new FilmData(line);
			films.add(film);
		}
		csvScan.close();
		return films;
	}

}
