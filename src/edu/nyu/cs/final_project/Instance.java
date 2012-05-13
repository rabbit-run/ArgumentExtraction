package edu.nyu.cs.final_project;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class Instance implements Serializable{
	private static final long serialVersionUID = 1L;
	private String label;
	private List<Set<String>> features;
	
	public Instance(String label, List<Set<String>> features) {
		this.label = label;
		this.features = features;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}

	public List<Set<String>> getFeatures() {
		return features;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("label: ").append(this.label).append("  features: ");
		sb.append(Utility.featuresToString(this.features));
		return sb.toString();
	}
}
