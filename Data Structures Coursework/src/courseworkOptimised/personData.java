package courseworkOptimised;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

public class personData<K extends Comparable<K>, V> {
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

	public static HashMap<personData, String> readFile(String filename) throws FileNotFoundException {
		HashMap<personData, String> persons = new HashMap<>();
		File csvFile = new File(filename);
		Scanner csvScan = new Scanner(csvFile);
		csvScan.nextLine();
		while (csvScan.hasNextLine()) {
			String line = csvScan.nextLine();
			personData person = new personData(line);
			persons.put(person, line);
		}
		csvScan.close();
		return persons;
	}

	private personData<K, V>[] buckets;
	private int occupied;

	public personData(K key, V value) {
		createBuckets(24931);
	}

	public void createBuckets(int size) {
		this.buckets = (personData<K, V>[]) new personData[size];
		occupied = 0;
	}

	public int hash(K key) {
		return key.hashCode();
	}

	public int compress(int hash) {
		return hash & (buckets.length - 1);
	}

	public personData<K, V> search(K key) {
		int index = hash(key);
		index = compress(index);

		personData<K, V> match = buckets[index];

		while (match != null && !match.key.equals(key)) {

			match = match.next;
		}
		return match;
	}

	public void put(K key, V value) {

		personData<K, V> pair = search(key);
		if (pair != null)
			pair.value = value;
		else {
			pair = new personData<K, V>(key, value);

			int index = hash(key);
			index = compress(index);

			personData<K, V> collision = buckets[index];
			if (collision != null) { // if so...
				int colcount = 1;
				while (collision.next != null) {
					collision = collision.next;
					colcount++;
				}
				System.out.println("Bucket " + index + " (" + colcount + " collisions), for " + key + ", " + value
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
		personData<K, V> newKeyValue = search(key);
		if (newKeyValue != null)
			return newKeyValue.value;
		else
			return null;
	}

	public V remove(K key) {
		personData<K, V> removingKeyValue = search(key);
		if (removingKeyValue != null) {

			int index = hash(key);
			index = compress(index);

			personData<K, V> e = buckets[index];
			personData<K, V> prev = e;
			while (e != null) {
				personData<K, V> next = e.next;

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

		for (personData<K, V> pair : buckets)
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

		Vector<personData<K, V>> keys = new Vector<personData<K, V>>();
		for (personData<K, V> pair : buckets)
			while (pair != null) {
				keys.add(pair);
				pair = pair.next;
			}

		createBuckets(newSize);
		for (personData<K, V> pair : keys)
			this.put(pair.key, pair.value);
	}

	public K key;
	public V value;
	public personData<K, V> next;

	public int compareTo(personData<K, V> comp) {
		return key.compareTo(comp.key);
	}

	public int hashCode() {
		return key.hashCode();
	}
}
