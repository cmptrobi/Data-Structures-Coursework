package courseworkOptimised;

import java.io.File;
import java.io.FileNotFoundException;
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


	// the buckets that hold the key/value pairs
	private FilmData<K, V>[] buckets;
	// keeps track of how many entries are held in this map
	private int occupied;

	public FilmData(K key, V value) {
		createBuckets(40); // initialise this map with 40 empty buckets
	}


	public void createBuckets(int size) {
		this.buckets = (FilmData<K, V>[]) new FilmData[size];
		occupied = 0;
	}

	public int hash(K key) {
		// TODO: At the moment, this method just returns 0.
		// it should generate a suitable hashcode for the key
		// (hint: Java can help us do this very easily)
		return 0;
	}

	// compresses the hashcode to the bounds of the underlying
	// bucket store
	public int compress(int hash) {
		// TODO: At the moment, this method just returns 0.
		// Assuming it is passed a valid hashcode, it should compress
		// the hashcode to fit in the bounds of the underlying bucket store
		// (i.e. it must become an integer between 0 and buckets.length)
		// hint: think modular arithmetic!
		return 0;
	}

	// searches the map for a key/value pair matching key
	// used by most of the other methods
	public FilmData<K, V> search(K key) {
		int index = hash(key);
		index = compress(index);

		FilmData<K, V> match = buckets[index];
		// TODO: At the moment, this method simply returns the entry for the
		// hashed bucket index. This will work in some cases.
		// However, what about collisions? If we look at the put(K, V) method, we'll see
		// that
		// this implementation uses SEPARATE CHAINING as a Collision Resolution
		// strategy.
		// Therefore, we then need to:
		// -check if this match actually matches the key using the equals() method
		// -check if the next entry in this bucket exists (i.e. match.next)
		// -repeat the above until a key match is found, or we reach the end of the
		// chain
		return match;
	}

	// puts the specified value into the map against the specified key
	// returns the previous value mapped against the key, if one existed
	public void put(K key, V value) {
		// locate the key/value pair, if stored in the map
		FilmData<K, V> pair = search(key);
		// if found, just replace the value
		if (pair != null)
			pair.value = value;
		else {
			pair = new FilmData<K, V>(key, value);

			int index = hash(key);
			index = compress(index);

			// see if something is already hashed here:
			FilmData<K, V> collision = buckets[index];
			if (collision != null) { // if so...
				// move to the last collision stored in this bucket
				while (collision.next != null) {
					collision = collision.next;
				}

				// read to end of other entries in this bucket, then place new
				// one at end of list
				collision.next = pair;
			} else
				// if no collision, just place it where the hashcode suggests
				buckets[index] = pair;

			occupied++; // increment occupied counter
		}
	}

	// gets the value matching the key
	// returns null if no key match was found
	public V get(K key) {
		FilmData<K, V> newKeyValue = search(key);
		if (newKeyValue != null)
			return newKeyValue.value;
		else
			return null;
	}

	// removes the key from the map, returning the existing
	// value if it existed
	// returns null if the key was not found
	public V remove(K key) {
		FilmData<K, V> removingKeyValue = search(key);
		if (removingKeyValue != null) {

			// get index position
			int index = hash(key);
			index = compress(index);

			// The removal is slightly more complex
			// as we might encounter a linked list
			// (SEPARATE CHAINING Collision resolution)

			FilmData<K, V> e = buckets[index];
			FilmData<K, V> prev = e;
			while (e != null) {
				FilmData<K, V> next = e.next;
				// if we've found the right key
				if (e.key.equals(key)) {
					if (prev == e) // if this is the first entry
						buckets[index] = next;
					else
						prev.next = next;
				}
				// go to next separately chained entry
				prev = e;
				e = next;
			}
			occupied--; // decrement occupied counter

			// return the old value
			return removingKeyValue.value;
		} else
			return null;
	}

	// returns the size of the map
	public int size() {
		return occupied;
	}

	// returns true if empty
	public boolean isEmpty() {
		return size() == 0;
	}

	// returns a set of the keys present in this map
	public Iterable<K> keys() {
		Vector<K> keys = new Vector<K>();

		// visit all buckets
		for (FilmData<K, V> pair : buckets)
			while (pair != null) {
				// walk the "chain" in each bucket
				keys.add(pair.key);
				pair = pair.next;
			}
		return keys;
	}

	public K key;
	public V value;
	// SEPARATE CHAINING Collision Resolution
	public FilmData<K, V> next;

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
			FilmData film = new FilmData(line);
			films.put(film, line);
		}
		csvScan.close();
		return films;
	}
}
