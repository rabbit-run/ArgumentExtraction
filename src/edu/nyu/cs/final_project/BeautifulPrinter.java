package edu.nyu.cs.final_project;

import static edu.nyu.cs.final_project.Utility.sentenceLiteral;

import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

import edu.stanford.nlp.trees.TreeGraphNode;

public class BeautifulPrinter {
	public static void printFeature(Set<List<String>> features) {
		PrintWriter pw = new PrintWriter(System.out);
		for (List<String> f : features) {
			StringBuilder sb = new StringBuilder();
			String prefix = "";
			for (String s : f) {
				sb.append(prefix);
				prefix = " | ";
				sb.append(s);
			}
			pw.print(sb.toString());
			pw.println();
		}
		pw.println();
		pw.flush();
	}
	
	public static void printNodes(List<TreeGraphNode> nodes) {
		PrintWriter pw = new PrintWriter(System.out);
		StringBuilder sb = new StringBuilder();
		String prefix = "";
		for (TreeGraphNode n : nodes) {
			sb.append(prefix);
			prefix = " | ";
			sb.append(n.label().value());
		}
		pw.println(sb.toString());
		pw.flush();
	}
}
