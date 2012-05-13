package edu.nyu.cs.final_project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SVMFileFormatter {
	private List<Instance> ins;
	
	public SVMFileFormatter(List<Instance> ins) {
		this.ins = ins;
	}
	
	public int[][] computeDevKernel(List<Instance> devIns) {	
		int[][] matrix = new int[devIns.size()][ins.size()];
		int outter, inner, res;
		
		for (outter = 0; outter < devIns.size(); outter ++) {
			for (inner = 0; inner < ins.size(); inner ++) {
				res = Utility.kernel(devIns.get(outter), ins.get(inner));
				matrix[outter][inner] = res;
			}
		}
		return matrix;
	}
	
	public int[][] computeTrainKernel(List<Instance> ins) {
		
		int[][] matrix = new int[ins.size()][ins.size()];
		int outter, inner, res;
		
		for (outter = 0; outter < ins.size(); outter ++) {
			for (inner = outter; inner<ins.size(); inner++) {
				res = Utility.kernel(ins.get(outter), ins.get(inner));
				if(inner == outter){
					matrix[outter][outter] = res;
				}
				else {
					matrix[outter][inner] = res;
					matrix[inner][outter] = res;
				}
			}
		}
		return matrix;
	}
	
	public void formatSVMFile(List<Instance> ins, int[][] matrix) {
		List<String> buffer = new ArrayList<String>();
		for (int i = 0; i<ins.size(); i++) {
			String label = ins.get(i).getLabel();
			int[] kernels = matrix[i];
			String line = formatLine(label, kernels, i+1);
			buffer.add(line);
		}
		
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("precomte_kernel.txt"));
			for (String line : buffer){
				out.write(line);
			}
			out.close();
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
			 
	}
	
	private String formatLine(String label, int[] kernels, int id) {
		StringBuilder sb = new StringBuilder();
		sb.append(label).append(" ").append("0:").append(id);
		
		for (int index = 0; index<kernels.length; index ++) {
			sb.append(" ").append(index+1).append(":").append(kernels[index]);
			sb.append("\n");
		}
		return sb.toString();
	}
}
