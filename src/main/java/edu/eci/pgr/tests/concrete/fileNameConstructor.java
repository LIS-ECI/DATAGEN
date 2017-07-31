package edu.eci.pgr.tests.concrete;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

public class fileNameConstructor {
	private static final String INPUT_FILE_NAME="names.in";
	private static final String OUTPUT_FILE_NAME="namesDG.out";
	private static final int LENGTH=15;
	public static void main(String[] args) {
		readRandomLine();
		//genereateFile();
		System.out.println("termino");
	}
	
	public static void genereateFile(){
		try {
			BufferedReader br = new BufferedReader( new FileReader(INPUT_FILE_NAME));
			BufferedWriter bw= new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME));
			int conta=0;
			while(br.ready()){
				String[] line= br.readLine().trim().split("-");
				for (int i = 0; i < line.length; i++) {
					line[i]=line[i].replace(" ", "");
					line[i]=line[i].replace("ñ", "n");
					line[i]=line[i].replace("í", "i");
					line[i]=line[i].replace("á", "a");
					line[i]=line[i].replace("é", "e");
					line[i]=line[i].replace("ó", "o");
					line[i]=line[i].replace("ú", "u");
					line[i]=line[i].replace("Í", "I");
					line[i]=line[i].replace("Á", "A");
					line[i]=line[i].replace("É", "E");
					line[i]=line[i].replace("Ó", "O");
					line[i]=line[i].replace("Ú", "U");
					if(!line[i].equals("")&& line[i].length()<15){
						for (int j = line[i].length(); j < LENGTH; j++) {
							line[i]+=" ";
						}
						//System.out.println(line[i].length());
						bw.write(line[i]+"\n");
						conta++;
					}

				}
			}
			System.out.println(conta);
			bw.close();
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void readRandomLine(){
		try {
			RandomAccessFile rand= new RandomAccessFile(OUTPUT_FILE_NAME,"r");
			long n=(long) (Math.random()*(rand.length()/(LENGTH+1)))-1;
			System.out.println(n);
			rand.seek(((LENGTH+1)*n));
			System.out.println(rand.readLine());
			rand.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
