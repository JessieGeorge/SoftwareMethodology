package com.example.jessie.android59.model;
import java.util.*;

public class RecordList {

	public static List<Record> recordList = new ArrayList<Record>();

	public static List<String> displayList = new ArrayList<String>();

	public static void addToRecordList(Record r) {

		recordList.add(r);
		displayList.add(r.title + " " + r.getDate());
	}

	public static void sortByTitle() {

		Collections.sort(recordList, new Comparator<Record>(){
			public int compare(Record r1, Record r2){
				return r1.title.compareToIgnoreCase(r2.title);
			}
		});

		Collections.sort(displayList, new Comparator<String>(){
			public int compare(String s1, String s2){
				return s1.compareToIgnoreCase(s2);
			}
		});
	}

	public static void sortByDate() {

		Collections.sort(recordList, new Comparator<Record>(){
			public int compare(Record r1, Record r2){
				return r1.date.compareTo(r2.date);
			}
		});

		Collections.sort(displayList, new Comparator<String>(){
			public int compare(String s1, String s2){
				return s1.substring(s1.length()-19).compareToIgnoreCase(s2.substring(s2.length()-19));
			}
		});
	}
}