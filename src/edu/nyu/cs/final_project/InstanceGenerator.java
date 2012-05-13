package edu.nyu.cs.final_project;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
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
		List<Instance> instances = new ArrayList<Instance>();
		// generate sample instances

		// for (List<String[]> sent : sentences) {
		for (int index = 0; index < allSentences.size(); index++) {
			List<String[]> sent = allSentences.get(index);
			InstanceExtractor ie = new InstanceExtractor(sent);
			System.out.println("sent number : " + index);
			if (!ie.getInstances(instances)){
				writeSer(instances);
				System.out.println("Error: index incompitable");
				System.exit(-1);
			}
		}

		writeSer(instances);
	}
	
	private void writeSer(List<Instance> instances) {
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
