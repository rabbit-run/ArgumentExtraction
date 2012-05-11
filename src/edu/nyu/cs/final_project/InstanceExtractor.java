package edu.nyu.cs.final_project;

import java.util.ArrayList;
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
		for (int i = 0; i<sent.size(); i++){
			String chunkTag = sent.get(i)[2];
			if(chunkTag.equals("B-PP")){
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
	
	public void getInstances() {
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
				List<TreeGraphNode> nodes = Trees.constructTree(Trees
						.getRawTree(sentenceLiteral(sent)));
				
				BeautifulPrinter.printNodes(nodes);
//				List<TreeGraphNode> path = Trees.findShortestPath(Trees.constructTree(Trees
//						.getRawTree(sentenceLiteral(sent))), index, indexOfPred);
//				
//				FeatureConstructor fc = new FeatureConstructor(path, sent);
//				Set<List<String>> res = fc.genFeatures();
//				BeautifulPrinter.printFeature(res);
				
			}
		}
	}
}
