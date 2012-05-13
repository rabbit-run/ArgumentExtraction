package edu.nyu.cs.final_project;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static edu.nyu.cs.final_project.Utility.*;
import edu.stanford.nlp.trees.TreeGraphNode;

public class InstanceExtractor {
	private List<String[]> sent;
	
	public InstanceExtractor(List<String[]> _sent) {
		sent = _sent;
	}
	
	private List<Integer> findHeadWords() {
		List<Integer> headWordsIndex = new ArrayList<Integer>();
		for (int i = 0; i<sent.size()-1; i++){
			String chunkTag = sent.get(i)[2];
			if(chunkTag.equals("B-PP")){
				String nextChunkTag = sent.get(i+1)[2];
				if (!nextChunkTag.equals("I-PP"))
					headWordsIndex.add(i);
			}
			
			if(chunkTag.equals("I-PP")) {
				String nextChunkTag = sent.get(i+1)[2];
				if (!nextChunkTag.equals("I-PP"))
					headWordsIndex.add(i);
			}
			
			if(chunkTag.equals("I-NP")) {
				String nextChunkTag = sent.get(i+1)[2];
				if (!nextChunkTag.equals("I-NP"))
					headWordsIndex.add(i);
			}
			
			if(chunkTag.equals("B-NP")) {
				String nextChunkTag = sent.get(i+1)[2];
				if (!nextChunkTag.equals("I-NP"))
					headWordsIndex.add(i);
			}
		}
		
		if (sent.get(sent.size()-1).equals("I-NP") ||
				sent.get(sent.size()-1).equals("B-NP") ||
				sent.get(sent.size()-1).equals("I-PP") ||
				sent.get(sent.size()-1).equals("I-PP"))
			headWordsIndex.add(sent.size()-1);			
		
		return headWordsIndex;
	}
	
	private int findPred() {
		for (int i = 0; i<sent.size(); i++){
			if ((sent.get(i).length == 7) && sent.get(i)[5].equals("PRED")) {
				return i;
			}
		}
		return -1;
	}
	
	public void removePunctuation() {
		Iterator<String[]> iter = sent.iterator();
		while (iter.hasNext()){
			String[] word = iter.next();
			String literal = word[0];
			String POStag = word[1];
			if (
					literal.equals("`") || 
					literal.equals("'") ||
					literal.equals("''") ||
					literal.equals("--") ||
					literal.equals(":") ||
					literal.equals("-") ||
					literal.equals(";") ||
					literal.equals("...") ||
					literal.equals("``") ||
					POStag.equals(".")) {
				iter.remove();
			}
		}
	}
	
	private String findLabel(String[] word) {
		if (word.length >= 6) {
			String tmp = word[5];
			if (tmp.equals("ARG0")) {
				return "ARG0";
			}
			else if (tmp.equals("ARG1")) {
				return "ARG1";
			}
			else if (tmp.equals("ARG2")) {
				return "ARG2";
			}
			else if (tmp.equals("ARG3")) {
				return "ARG1";
			}
		}
		return "NA";
	}
	
	public boolean getInstances(List<Instance> instances) {
		// construct the dependency tree
		List<TreeGraphNode> nodes = Trees.constructTree(Trees
				.genRawTreeWithTag(formatSentence(sent)));
		
		// remove punctuation in the sentence, so that the index of 
		// sent and nodes are the synchronized.
		removePunctuation();

		if (sent.size() != nodes.size()) {
			System.out.println("sent\tnode");
			System.out.println(sent.size()+"\t"+nodes.size());
			return false;
		}
		/**
		// test
		int len = Math.max(sent.size(), nodes.size());
//		System.out.println(sentenceLiteral(sent));
		System.out.println("length of sent : " + sent.size());

//		BeautifulPrinter.printNodes(nodes);
		System.out.println("length of nodes : " + nodes.size());
		System.out.println("length should be : " + len);		
		System.out.println("sent    __    nodes");
		for (int index = 0; index < len; index ++) {
			System.out.println(sent.get(index)[0] + "  __  "
					+ nodes.get(index).label().value());
		}
		
		System.out.println();
		
		
		if (sent.size()!=nodes.size()) {
			System.out.println("Error: length not match");			
			System.exit(-1);
		}
		**/
		
		// only consider head words
		List<Integer> indexOfHeadWords = findHeadWords();
		int indexOfPred = findPred();
		if (indexOfPred == -1) {
			System.out.println("[INSTANCE_EXTRACTOR]: get pred index error");
			System.exit(-1);
		}
		
		for (Integer index : indexOfHeadWords) {
			// if the word is the same as predicate, just ignore it
			if (index != indexOfPred) {
				String[] word = sent.get(index);
				// ARGM is not considered in this task, just ignore it
				if (word.length == 6 && word[5].equals("ARGM"))
					continue;
				// find shortest path, and generate the features.
				List<TreeGraphNode> path = Trees.findShortestPath(nodes, index, indexOfPred);
				
				FeatureConstructor fc = new FeatureConstructor(path, sent);
				List<Set<String>> features = fc.genFeatures();
				String label = findLabel(word);
				instances.add(new Instance(label, features));
			}
		}
		return true;
	}
	
}
