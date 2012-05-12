package edu.nyu.cs.final_project;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

public class InstanceGenerator {
	private List<List<String[]>> allSentences;
	private String instanceFileName;
	
	public InstanceGenerator(String instanceFileName) {
		this.instanceFileName = instanceFileName;
		SentenceSplitter ss = new SentenceSplitter(instanceFileName);
		allSentences = ss.getAllSentences();
		Trees.initial();
	}
	
	public void genInstanceFile() {
		List<Instance> instances = new LinkedList<Instance>();
		// generate sample instances

		// for (List<String[]> sent : sentences) {
		for (int index = 0; index < allSentences.size(); index++) {
			List<String[]> sent = allSentences.get(index);
			InstanceExtractor ie = new InstanceExtractor(sent);
			System.out.println("sent number : " + index);
			ie.getInstances(instances);
		}

		try {
			FileOutputStream fileOut = new FileOutputStream(instanceFileName+".ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(instances);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}
}
