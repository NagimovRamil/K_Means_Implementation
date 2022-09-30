package main.java.com.ramilnagimov.iris;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadDataset {

	protected List<double[]> features = new ArrayList<>();
	protected static int numberOfFeatures;

	void read(String s) throws NumberFormatException, IOException {

		File file=new File(s);

		try {
			FileReader fr = new FileReader(file);
			BufferedReader readFile = new BufferedReader(fr);
			String line;
			while((line = readFile.readLine()) != null)
			{

				String[] split = line.split(",");
				double[] feature = new double[split.length - 1];
				numberOfFeatures = split.length-1;
				for (int i = 0; i < split.length - 1; i++)
					feature[i] = Double.parseDouble(split[i]);
				features.add(feature);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
