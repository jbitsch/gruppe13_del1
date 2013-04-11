package weightInterface;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FileReader {
	FileInputStream fstream;
	DataInputStream in;
	BufferedReader br;
	String strLine;
	
	public String checkNum(int num, String file, int col) throws IOException
	{
		fstream = new FileInputStream(file);
		in = new DataInputStream(fstream);
		br = new BufferedReader(new InputStreamReader(in));

		while ((strLine = br.readLine()) != null)   {
			String delimiter = ";";
			String temp[] = strLine.split(delimiter);

			int num2 = Integer.parseInt(temp[0]);
			
			if(num==num2)
			{
				br.close();
				in.close();
				fstream.close();
				return temp[col];
			}
		}

		br.close();
		in.close();
		fstream.close();
		return null;
	}
	
	
	public double updateStore(double netto, int productNo) throws IOException
	{
		fstream = new FileInputStream("store.txt");
		in = new DataInputStream(fstream);
		br = new BufferedReader(new InputStreamReader(in));
		double storeToReturn=0.00;
		String tempFile = "";
		
		while ((strLine = br.readLine()) != null)   {
			String delimiter = ";";
			String row[] = strLine.split(delimiter);
			
			int pNum = Integer.parseInt(row[0]);
			String pName = row[1];
			double store = Double.parseDouble(row[2]);
			
			if( pNum== productNo)
			{
				
				store = store - netto;
				storeToReturn = store;
			}
			String toAdd = pNum+";"+pName+";"+store;
			tempFile = tempFile + toAdd + "\n";
		}
		
		fstream.close();
		in.close();
		br.close();
		
		updateFile(tempFile, "store.txt", false);
		return storeToReturn;
	}
	
	
	public void updateFile(String text, String file, boolean noOverwrite) throws IOException
	{
		FileWriter fileWriter = new FileWriter(file, noOverwrite);
		BufferedWriter out = new BufferedWriter(fileWriter);
		out.write(text);
		//make newline
		if(noOverwrite)
			out.newLine();
		out.close();
		fileWriter.close();
		
	}
}
