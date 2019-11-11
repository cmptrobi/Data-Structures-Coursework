package coursework;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class personData {

	public final String SEP = ",";
	public String tconst;
	public String ordering;
	public String nconst;
	public String category;
	public String job;
	public String characters;

	public personData(String csvString) {
		String[] csvParts = lineSplit(csvString, SEP);
		int idx = 0;
		tconst = csvParts[idx++];
		ordering = csvParts[idx++];
		nconst = csvParts[idx++];
		category = csvParts[idx++];
		job = csvParts[idx++];
		characters = csvParts[idx++];
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
		return tconst + SEP + ordering + SEP + nconst + SEP + category + SEP + job + SEP + characters; 
	}

	public static ArrayList<personData> readFile(String filename) throws FileNotFoundException {
		ArrayList<personData> persons = new ArrayList<>();
		File csvFile = new File(filename);
		Scanner csvScan = new Scanner(csvFile);
		csvScan.nextLine();
		while (csvScan.hasNextLine()) {
			String line = csvScan.nextLine();
			personData person = new personData(line);
			persons.add(person);
		}
		csvScan.close();
		return persons;
	}

}
