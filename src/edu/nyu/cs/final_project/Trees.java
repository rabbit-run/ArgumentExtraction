package edu.nyu.cs.final_project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.PennTreebankLanguagePack;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeGraphNode;
import edu.stanford.nlp.trees.TreePrint;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;

public class Trees {
	private static LexicalizedParser lp;
	private Trees() {
		lp = new LexicalizedParser(
				"englishPCFG.ser.gz");
	}
	
	public static Trees initial() {
		Trees t = new Trees();
		return t;
	}
	
	public static List<TypedDependency> getRawTree(String sentence) {
		lp.setOptionFlags("-maxLength", "80", "-retainTmpSubcategories");
		TreebankLanguagePack tlp = new PennTreebankLanguagePack();
		GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
		TreePrint tp = new TreePrint("penn");
		
		//testing sentences
//		String[] sent = { "This", "is", "an", "easy", "sentence", "." };
//		List<HasWord> words = new ArrayList<HasWord>();
//		for (String s: sent) {
//			words.add(new Word(s));
//		}
//		String sentence = "But about 25% of the insiders, acording to SEC figures, " +
//				"file their reports late.";
//		String sentence1 = "Bills on ports and immigration were submitted by " +
//				"Senator Brownback, Republican of Kansas.";
//		String sentence2 = "Bell, based in Los Angeles, makes and distributes " +
//				"electronic, computer and building products.";
		Tree parse = lp.apply(sentence);
		GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
		List<TypedDependency> tdl = gs.typedDependencies(false);
		
		return tdl;
	}

	
	public static List<TreeGraphNode> constructTree(List<TypedDependency> tdl) {
		List<TreeGraphNode> nodes = new ArrayList<TreeGraphNode>();
		Map<TreeGraphNode, List<TreeGraphNode>> children = 
				new HashMap<TreeGraphNode, List<TreeGraphNode>>();
		
		for (TypedDependency td : tdl) {
			nodes.add(td.dep());
			children.put(td.dep(), new ArrayList<TreeGraphNode>());
		}
		
		for (TypedDependency td : tdl) {
			if (!td.gov().label().value().equals("ROOT")) {
				if (!children.get(td.dep()).contains(td.gov())) {
					children.get(td.dep()).add(td.gov());
				}
				if (!children.get(td.gov()).contains(td.dep())) {
					children.get(td.gov()).add(td.dep());
				}
			}
		}
		
		for (TreeGraphNode node : nodes) {
			node.setChildren(children.get(node));
		}
		
		return nodes;
	}
	
	public static List<TreeGraphNode> findShortestPath(
			List<TreeGraphNode> nodes, int indexOfStart, int indexOfDes) {
		TreeGraphNode root = nodes.get(indexOfStart);
		TreeGraphNode des = nodes.get(indexOfDes);
		List<TreeGraphNode> visitedNodes  = new ArrayList<TreeGraphNode>();
		Queue<TreeGraphNode> q = new LinkedList<TreeGraphNode>();
		Map<TreeGraphNode, TreeGraphNode> previous = new HashMap<TreeGraphNode, TreeGraphNode>();
		visitedNodes.add(root);
		
		q.offer(root);
		while(!q.isEmpty()){
			TreeGraphNode node = q.poll();
			if (node.equals(des)){
				break;
			}
			
			for (TreeGraphNode child : node.children()){
				if (!visitedNodes.contains(child)){
					previous.put(child, node);
					visitedNodes.add(child);
					q.offer(child);
				}
			}
		}
		return decode(previous, root, des);
	}
	
	private static List<TreeGraphNode> decode(Map<TreeGraphNode, TreeGraphNode> previous,
			TreeGraphNode src, TreeGraphNode des) {
		List<TreeGraphNode> path = new LinkedList<TreeGraphNode>();
		TreeGraphNode parent = previous.get(des);
		path.add(des);
		
//		System.out.println("src : " + src);
//		System.out.println("des : " + des);		
		
		while (parent != src) {
			path.add(parent);
			parent = previous.get(parent);
		}
		path.add(src);
		Collections.reverse(path);
		return path;
	}
}
