package edu.nyu.cs.final_project;

import java.util.List;

import edu.stanford.nlp.trees.TreeGraphNode;

public class Utility {
	public static void constructFeatureFile(List<List<String[]>> sentences) {
		for (List<String[]> sent : sentences) {
			
			
		}
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
