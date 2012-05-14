package edu.nyu.cs.final_project;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import edu.stanford.nlp.trees.TreeGraphNode;
import edu.stanford.nlp.trees.TypedDependency;

public class Main {
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Error: please specify a file name and type (-train or -dev)");
			System.exit(-1);
		}
		String type = args[0];
		String fileName = args[1];
		// Generate instance file takes really loooooooooooong time
		// be careful :)
		if (!(new File(fileName + ".ser")).exists()) {
			InstanceGenerator ig = new InstanceGenerator(fileName);
			ig.genInstanceFile();
		}
		List<Instance> ins = Utility.deserialize(fileName + ".ser");
		List<Instance> trainIns = Utility.deserialize("training_2458.ser");		
		System.out.println(trainIns.size());
		
//		//convert string label to int
//		Utility.convertLabel(trainIns);
//		Utility.convertLabel(ins);	
//		SVMFileFormatter svm = new SVMFileFormatter(fileName, trainIns);
//		if (type.equals("-train")) {
//			svm.formatSVMFile(12000);
//		}
//		else if (type.equals("-dev")){
//			svm.formatDevFile(ins, 12000);
//		}
	}

	private static void testLabel() {

	}

	private static void testMatrix() {
		int[][] M = new int[3][3];
		for (int i = 1; i <= 3; i++) {
			for (int j = 1; j <= 3; j++) {
				M[i - 1][j - 1] = i * j;
			}
		}

		for (int i : M[1]) {
			System.out.println(i);
		}
	}

	private static void testDeserialize() {
		List<Instance> il = Utility.deserialize("");
		List<Set<String>> features = il.get(0).getFeatures();
		for (Set<String> s : features) {
			System.out.println(s);
		}
	}

	private static void testInstanceGen() {
		InstanceGenerator ig = new InstanceGenerator("test");
		ig.genInstanceFile();
	}

	// sentence 103, problem with char "-"
	private static void testConstructTree() {
		SentenceSplitter ss = new SentenceSplitter("dev");
		List<List<String[]>> all = ss.getAllSentences();
		Trees.initial();
		List<String[]> sent = all.get(103);
		List<TypedDependency> tdl = Trees.genRawTreeWithTag(Utility
				.formatSentence(sent));
		for (TypedDependency t : tdl) {
			System.out.println(t);
		}
		Trees.constructTree(tdl);
	}

	// generate denpendency tree with pre tagged data
	private static void testPreTag() {
		SentenceSplitter ss = new SentenceSplitter("dev");
		List<List<String[]>> all = ss.getAllSentences();
		Trees.initial();
		List<String[]> sent = all.get(52);
		List<TreeGraphNode> nodes = Trees.constructTree(Trees
				.genRawTreeWithTag(Utility.formatSentence(sent)));
		BeautifulPrinter.printNodes(nodes);
	}

	private static void printSomeThing() {
		SentenceSplitter ss = new SentenceSplitter("dev");
		List<List<String[]>> all = ss.getAllSentences();
		List<String[]> sent = all.get(0);
		System.out.println(Utility.sentenceLiteral(sent));
		Trees.initial();
		List<TypedDependency> tdl = Trees.genRawTree(Utility
				.sentenceLiteral(sent));
		for (TypedDependency td : tdl) {
			System.out.println(td);
		}
	}

	private static void testReplace() {
		List<String> sent = Lists.newArrayList("''", ".", "asd", "bbb", ".",
				"''");
		int i;
		Iterator<String> iter = sent.iterator();
		while (iter.hasNext()) {
			String literal = iter.next();
			if (literal.equals("COMMA") || literal.equals("'")
					|| literal.equals("''") || literal.equals(".")
					|| literal.equals("(") || literal.equals(")")
					|| literal.equals("{") || literal.equals("}")
					|| literal.equals("``")) {
				iter.remove();
			}
		}
		for (String s : sent) {
			System.out.println(s);
		}
	}

	private static void testRmPunc() {
		SentenceSplitter ss = new SentenceSplitter("dev");
		List<List<String[]>> all = ss.getAllSentences();
		List<String[]> sent = all.get(2);
		InstanceExtractor ie = new InstanceExtractor(sent);
		ie.removePunctuation();
		System.out.println(Utility.sentenceLiteral(sent) + " ~~~len : "
				+ sent.size());
	}

	private static void testNow() {
		SentenceSplitter ss = new SentenceSplitter("dev");
		List<List<String[]>> all = ss.getAllSentences();

		List<String[]> sent = all.get(0);
		// System.out.println(Utility.sentenceLiteral(sent) + " ~~~len : " +
		// sent.size());

	}

	private static void testSetAddNull() {
		PrintWriter pw = new PrintWriter(System.out);
		Set<String> s = new HashSet<String>();
		s.add("lol");
		s.add(null);
		for (String str : s) {
			pw.println(str);
		}
		pw.flush();
	}

	private static void testCartesianProduct() {
		PrintWriter pw = new PrintWriter(System.out, true);

		HashSet<String> wc1 = Sets.newHashSet("protester", "NNS", "Noun",
				"PERSON");
		HashSet<String> wc2 = Sets.newHashSet("seized", "VDB", "Verb");
		HashSet<String> wc3 = Sets.newHashSet("stations", "NNS", "Noun",
				"FALCILITY");
		@SuppressWarnings("unchecked")
		List<HashSet<String>> s = Lists.newArrayList(wc1, wc2, wc3);
		Set<List<String>> res = Sets.cartesianProduct(s);
		for (List<String> ls : res) {
			for (String str : ls) {
				pw.print(str);
				pw.print(" ");
			}
			pw.println();
		}
		pw.println("length : " + res.size());
		pw.flush();
	}
}
