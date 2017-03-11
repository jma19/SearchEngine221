package com.uci.utils;

import java.io.*;
import java.util.*;

public class ComputeNDCG {
	public List<String> readFile(String path) {
		List<String> oURLs = new ArrayList<String>();
		try{
		File f1 = new File(path);
		FileReader or = new FileReader(f1);
		BufferedReader obr = new BufferedReader(or);
		
		int time = 5;
		while(time --> 0){
			oURLs.add(obr.readLine());
		}
		obr.close();
		or.close();
	}catch (Exception e){
		 e.printStackTrace();

	}
	return oURLs;
}

	public void NDCG(String us, String google){
		List<String> gURLs = readFile(google);
		//System.out.println(gURLs);
		List<String> oURLs = readFile(us);
		Double[] ideal = new Double[5];
		Double[] dcg = new Double[5];
		Double[] ndcg = new Double[5];
		Double score = 5.0;
		for(int i = 0; i < 5; i++){
			if(i ==0) {
				ideal[i] = score;
				dcg[i] = 0.0;
				for(int j = 0; j < 5; j++){
					if(oURLs.get(i).equals(gURLs.get(j))){
						dcg[i] = 5.0 - j;
						break;
					}
				}
			}else{
				ideal[i] = ideal[i-1] + score/(Math.log(i+1)/Math.log(2));
				dcg[i] = dcg[i-1];
				for(int j = 0; j < 5; j++){
					if(oURLs.get(i).equals(gURLs.get(j))){
						Double tmp = 5.0 - j;
						dcg[i] = dcg[i-1] + tmp/(Math.log(i+1)/Math.log(2));
						break;
					}
				}

			}
			score = score - 1;
		}
		for(int i = 0; i < 5; i++){
			ndcg[i] = dcg[i]/ideal[i];
			System.out.println("local DCG" + (i+1) + ":" + dcg[i]);
			System.out.println("ideal DCG" + (i+1) + ":" + ideal[i]);
			System.out.println("NDCG" + (i+1) + ":" + ndcg[i]);
			System.out.println();
		}


	}
	public static void main(String[] args){
		ComputeNDCG c = new ComputeNDCG();
		String i = "ideal.txt";
		String j = "local.txt";
		c.NDCG(i,j);
	}
}