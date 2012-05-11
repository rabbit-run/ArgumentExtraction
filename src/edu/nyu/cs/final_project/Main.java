package edu.nyu.cs.final_project;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.PennTreebankLanguagePack;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreePrint;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;

public class Main {
	public static void main(String[] args) {
		testNow();
	}

	private static void testNow() {
		SentenceSplitter ss = new SentenceSplitter("training");
		List<List<String[]>> all = ss.getAllSentences();
		
		
		List<String[]> sent = all.get(0);
		Trees.initial();
		System.out.println(Utility.sentenceLiteral(sent) + " ~~~len : " + sent.size());
		InstanceExtractor ie = new InstanceExtractor(sent);
		ie.getInstances();
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
