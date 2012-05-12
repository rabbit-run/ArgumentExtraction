package edu.nyu.cs.final_project;

import java.util.List;
import java.util.Set;

public class Instance {
	private String label;
	private List<Set<String>> features;
	
	public Instance(String label, List<Set<String>> features) {
		this.label = label;
		this.features = features;
	}

	public String getLabel() {
		return label;
	}

	public List<Set<String>> getFeatures() {
		return features;
	}
	
	
}
