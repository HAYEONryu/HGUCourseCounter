package edu.handong.analysis.utils;
import java.util.ArrayList;
import java.io.*;
import java.io.BufferedWriter;


public class Utils {
	public static ArrayList<String> getLines(String file,boolean removeHeader){
		
			ArrayList<String> cvslist = new ArrayList<String>();

			String line = null;

			File filepath = new File(file);

			try {

				BufferedReader br = new BufferedReader(new FileReader(filepath));

				if(removeHeader == true) {
					br.readLine();
				}
				
				while( (line = br.readLine()) != null) {

					String strings[] = line.split(",");
					for(String str : strings)
						cvslist.add(str.trim());
				}
				br.close();
			}catch (Exception e) {
				 System.out.println ("The file path does not exist. Please check your CLI argument!");
			}
	
		return cvslist;
	}
	
	public static void writeAFile(ArrayList<String> lines, String targetFileName) {
		int colums = 0;
		try{
			BufferedWriter fw;
			if(targetFileName.contains("C:\\"))
			{
				fw = new BufferedWriter(new FileWriter(targetFileName+".csv", true));
			}
			else
			{
				fw = new BufferedWriter(new FileWriter("C:\\Users\\User\\Downloads\\" + targetFileName+".csv", true));
			}
		for(String w : lines){
			colums++;
			fw.write(w);
			fw.write(",");
			if( colums%4 == 0) {
				fw.newLine();
			}
		}
		fw.close();
		}catch (Exception e) {
		
			e.printStackTrace();
		}		
	}
}
