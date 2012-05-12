package edu.nyu.cs.final_project;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;

import edu.stanford.nlp.trees.TreeGraphNode;

public class FeatureConstructor {
	private List<TreeGraphNode> path;
	private List<String[]> sent; 
	
	public FeatureConstructor(List<TreeGraphNode> _path, List<String[]> sent){
		path = _path;
		this.sent = sent;
	}
	
	public List<Set<String>> genFeatures(){
		List<Set<String>> wordClassList = new ArrayList<Set<String>>();
		for (TreeGraphNode tg: path) {
			int index = -1;
			for (int i = 0; i<sent.size(); i++) {
				String[] word = sent.get(i);
				if (tg.label().value().equals(word[0])){
					index = i;
					break;
				}
			}
			
			Set<String> wordClasses = genWordClasses(index);
			wordClassList.add(wordClasses);
		}
		return wordClassList;
	}
	
	
	private Set<String> genWordClasses(int index){
		Set<String> wordClass = new HashSet<String>();
		// word literal
		wordClass.add(sent.get(index)[0]);
		// POS tag
		wordClass.add(getPOS(index));
		// PRED or SUPPORT
		String predOrSupport = getPredOrSupport(index);
		if (null != predOrSupport) {
			wordClass.add(predOrSupport);
		}
		// class of PRED
		String predClass = getPredClass(index);
		if (null != predClass) {
			wordClass.add(predClass);
		}
		return wordClass;
	}
	
	private String getPredClass(int index){
		String[] word = sent.get(index);
		if (word.length == 7 && word[5].equals("PRED")) {
			return word[6];
		}
		return null;
	}
	
	private String getPredOrSupport(int index) {
		String[] word = sent.get(index);
		if (word.length >= 6) {
			if (word[5].equals("PRED"))
				return "PRED";
			if (word[5].equals("SUPPORT"))
				return "SUPPORT";
		}
		return null;
	}
	
	private String getPOS(int index) {
		return sent.get(index)[1];
	}
}
