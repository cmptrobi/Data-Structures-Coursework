package courseworkOptimised;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

import courseworkOptimised.Menu.regionType;

public class FilmData<K extends Comparable<K>, V> {

	public final String SEP = ",";
	public String titleId;
	public String ordering;
	public String title;
	public regionType region;
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
		region = regionType.getFrom(csvParts[idx++]);
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

	private FilmData<K, V>[] buckets;

	private int occupied;

	public FilmData(K key, V value) {
		createBuckets(4602);
	}

	public void createBuckets(int size) {
		this.buckets = (FilmData<K, V>[]) new FilmData[size];
		occupied = 0;
	}

	public int hash(K key) {
		return key.hashCode();
	}

	public int compress(int hash) {
		return hash & (buckets.length - 1);
	}

	public FilmData<K, V> search(K key) {
		int index = hash(key);
		index = compress(index);

		FilmData<K, V> match = buckets[index];

		while (match != null && !match.key.equals(key)) {

			match = match.next;
		}
		return match;
	}

	public void put(K key, V value) {
	
			FilmData<K, V> pair = search(key);
			if (pair != null)
				pair.value = value;
			else {
				pair = new FilmData<K, V>(key, value);

				int index = hash(key);
				index = compress(index);

				FilmData<K, V> collision = buckets[index];
				if (collision != null) { // if so...
					int colcount = 1;
					while (collision.next != null) {
						collision = collision.next;
						colcount++;
					}
					System.out.println("Bucket " + index + " (" + colcount
							+ " collisions), for " + key + ", " + value
							+ " - with " + collision.key);

					collision.next = pair;
				} else
					buckets[index] = pair;
				occupied++;
			}


			if (loadFactor() > 0.75d)
				rehash(buckets.length * 2);
		}

	public V get(K key) {
		FilmData<K, V> newKeyValue = search(key);
		if (newKeyValue != null)
			return newKeyValue.value;
		else
			return null;
	}

	public V remove(K key) {
		FilmData<K, V> removingKeyValue = search(key);
		if (removingKeyValue != null) {

			int index = hash(key);
			index = compress(index);

			FilmData<K, V> e = buckets[index];
			FilmData<K, V> prev = e;
			while (e != null) {
				FilmData<K, V> next = e.next;

				if (e.key.equals(key)) {
					if (prev == e)
						buckets[index] = next;
					else
						prev.next = next;
				}

				prev = e;
				e = next;
			}
			occupied--;

			return removingKeyValue.value;
		} else
			return null;
	}

	public int size() {
		return occupied;
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	public Iterable<K> keys() {
		Vector<K> keys = new Vector<K>();

		for (FilmData<K, V> pair : buckets)
			while (pair != null) {
				keys.add(pair.key);
				pair = pair.next;
			}
		return keys;
	}
	
	public double loadFactor() {
		return (double) size() / buckets.length;
	}
	public void rehash(int newSize) {
		System.out.println("***---REHASHING to " + newSize);

		
		Vector<FilmData<K, V>> keys = new Vector<FilmData<K, V>>();
		for (FilmData<K, V> pair : buckets)
			while (pair != null) {
				keys.add(pair);
				pair = pair.next;
			}

	
		createBuckets(newSize);
		for (FilmData<K, V> pair : keys)
			this.put(pair.key, pair.value);
	}

	public K key;
	public V value;
	public FilmData<K, V> next;
	private int length;

	public int compareTo(FilmData<K, V> comp) {
		return key.compareTo(comp.key);
	}

	public int hashCode() {
		return key.hashCode();
	}
	

	

	public static HashMap<FilmData, String> readFile(String filename) throws FileNotFoundException {
		HashMap<FilmData, String> films = new HashMap<>();
		File csvFile = new File(filename);
		Scanner csvScan = new Scanner(csvFile);
		csvScan.nextLine();
		while (csvScan.hasNextLine()) {
			String line = csvScan.nextLine();
			FilmData film = new FilmData(line)
			films.put(film, line);
		}
		csvScan.close();
		return films;
	}
}
