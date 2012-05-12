package edu.nyu.cs.final_project;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;

import edu.stanford.nlp.ling.TaggedWord;

public class Utility {
	public static void constructFeatureFile(List<List<String[]>> sentences) {
		Trees.initial();
		List<Instance> instances = new LinkedList<Instance>();
		// generate sample instances
		
//		for (List<String[]> sent : sentences) {
		for (int index = 0; index < sentences.size(); index++){
			List<String[]>sent = sentences.get(index);
			InstanceExtractor ie = new InstanceExtractor(sent);
			System.out.println("sent number : " + index);
			ie.getInstances(instances);
		}
		System.out.println("This could be huge ~ : " + instances.size());
	}

	public static <T> int similar(Set<T> setA, Set<T> setB) {
		int count = 0;
		for (T x : setA)
			if (setB.contains(x)){
				count++;
			}
		return count;
	}
	
	public static int kernel(Instance x, Instance y) {
		List<Set<String>> x_features = x.getFeatures();
		List<Set<String>> y_features = y.getFeatures();
		if (x_features.size() != y_features.size()) {
			return 0;
		}
		else {
			int res = 1;
			for (int i = 0; i<x_features.size(); i++) {
				res*= similar(x_features.get(i), y_features.get(i));
			}
			return res;
		}
	}
	
	public static List<TaggedWord> formatSentence(List<String[]> sent) {
//		String prefix = "";
//		StringBuilder sb = new StringBuilder();
		List<TaggedWord> twl = Lists.newArrayList();
		for (String[] word : sent) {
			String literal = word[0];
			String POStag;
			TaggedWord tw;
			if (word[1].equals("COMMA")){
				POStag = ",";
				tw = new TaggedWord(literal, POStag);
				twl.add(tw);
			}
			else if (!word[1].equals("-")) {
				POStag = word[1];
				tw = new TaggedWord(literal, POStag);
				twl.add(tw);
			}
//			sb.append(prefix);
//			prefix = " ";
//			sb.append(literal).append("/").append(POStag);
		}
		return twl;
	}
	
	
	public static String sentenceLiteral(List<String[]> sent){
		String prefix = "";
		StringBuilder sb = new StringBuilder();
		for (String[] word : sent){
			String literal = word[0];
			if (literal.equals("COMMA")){
				sb.append(",");
			}
			else {
				sb.append(prefix);
				prefix = " ";
				sb.append(literal);
			}
		}
		return sb.toString();
	}
}
