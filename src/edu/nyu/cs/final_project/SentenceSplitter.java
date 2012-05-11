package edu.nyu.cs.final_project;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class SentenceSplitter {
	private String fileName;
	private List<List<String[]>> allSentences;
	private ArrayList<String[]> sent;
	
	public SentenceSplitter(String fileName) {
		this.fileName = fileName;
		sent = new ArrayList<String[]>();
		allSentences = new LinkedList<List<String[]>>();
	}
	
	private void splitSentence() {
		FileReader fr;
		try{
			fr = new FileReader(fileName);
			Scanner sc = new Scanner(fr);
			while( sc.hasNextLine()) {
				processLine(sc.nextLine());
			}
		}
		catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	private void processLine(String line) {
		
		if (line.isEmpty()) {
			allSentences.add((List<String[]>) sent.clone());
			sent.clear();
		}
		else {
			String[] s = line.trim().split("\\s");
			sent.add(s);
		}
	}
	
	public List<List<String[]>> getAllSentences() {
		splitSentence();
		return allSentences;
	}
}
