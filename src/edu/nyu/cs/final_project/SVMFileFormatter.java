package edu.nyu.cs.final_project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SVMFileFormatter {
	private List<Instance> ins;
	private String fileName;
	
	public SVMFileFormatter(String fileName, List<Instance> ins) {
		this.ins = ins;
		this.fileName = fileName;
	}
	
//	public int[][] computeDevKernel(List<Instance> devIns) {	
//		int[][] matrix = new int[devIns.size()][ins.size()];
//		int outter, inner, res;
//		
//		for (outter = 0; outter < devIns.size(); outter ++) {
//			for (inner = 0; inner < ins.size(); inner ++) {
//				res = Utility.kernel(devIns.get(outter), ins.get(inner));
//				matrix[outter][inner] = res;
//			}
//		}
//		return matrix;
//	}
	
//	public int[][] computeTrainKernel() {
//		int[][] matrix = new int[ins.size()][ins.size()];
//		int outter, inner, res;
//		
//		for (outter = 0; outter < ins.size(); outter ++) {
//			for (inner = outter; inner<ins.size(); inner++) {
//				res = Utility.kernel(ins.get(outter), ins.get(inner));
//				if(inner == outter){
//					matrix[outter][outter] = res;
//				}
//				else {
//					matrix[outter][inner] = res;
//					matrix[inner][outter] = res;
//				}
//			}
//
//		}
//		return matrix;
//	}
	
	public void formatSVMFile() {
		int[] kernels = new int[ins.size()];
		int outter, inner, res;
		System.out.println("processing");
		for (outter = 0; outter<ins.size(); outter++) {
			//test
			if (outter % 1000 == 0){
				System.out.println("processed: " + outter);
			}
			for (inner = 0; inner<ins.size(); inner++) {
				res = Utility.kernel(ins.get(outter), ins.get(inner));
				kernels[inner] = res;
			}
			String line = formatLine(ins.get(outter).getLabel(), kernels, outter);
			appendToFile(line);
		}	 
	}
	
	private String formatLine(String label, int[] kernels, int id) {
		StringBuilder sb = new StringBuilder();
		sb.append(label).append(" ").append("0:").append(id+1);
		
		for (int index = 0; index<kernels.length; index ++) {
			sb.append(" ").append(index+1).append(":").append(kernels[index]);
		}
		return sb.toString();
	}
	
	private void appendToFile(String line) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(fileName
					+ "_pre_kernel.txt", true));
			out.write(line);
			out.newLine();
			out.close();
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
