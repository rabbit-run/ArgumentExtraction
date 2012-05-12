package edu.nyu.cs.final_project;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;

import edu.stanford.nlp.ling.TaggedWord;

public class Utility {
	@SuppressWarnings("unchecked")
	public static List<Instance> deserialize() {
		List<Instance> ins = null;
		try
        {
           FileInputStream fileIn =
                         new FileInputStream("instances.ser");
           ObjectInputStream in = new ObjectInputStream(fileIn);
           ins = (List<Instance>) in.readObject();
           in.close();
           fileIn.close();
       }catch(IOException i)
       {
           i.printStackTrace();
           System.exit(1);
       }catch(ClassNotFoundException c)
       {
           System.out.println("Employee class not found.");
           c.printStackTrace();
           System.exit(1);
       }
		return ins;
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
