package main.java.com.ramilnagimov.iris;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class KClusterer extends ReadDataset {

	public static void main(String[] args) throws IOException {
		ReadDataset readDataset = new ReadDataset();
		readDataset.features.clear();
		Scanner sc = new Scanner(System.in);
		System.out.println("Введите имя файла содержащего датасет с путем");
		String file = sc.next();
		readDataset.read(file);
		System.out.println("Введите количество кластеров");
		int numberOfClusters = sc.nextInt();
		System.out.println("Введите максимальное количество итераций");
		int maxIterations = sc.nextInt();
		// мапа для хранения центров и признаков
		Map<Integer, double[]> centroids = new HashMap<>();
		// количество центров в зависимости от кол-ва признаков
		double[] featureValue;
		int counterForFeatureValueGet = 0;
		for (int i = 0; i < numberOfClusters; i++) {
			featureValue = readDataset.features.get(counterForFeatureValueGet++);
			centroids.put(i, featureValue);
		}
		// мапа для признаков и соответствующих кластеров
		Map<double[], Integer> clusters;
		clusters = kmeans(readDataset.features, centroids, numberOfClusters);
		double db[];
		// перемещение в новый кластер
		for (int i = 0; i < maxIterations; i++) {
			for (int j = 0; j < numberOfClusters; j++) {
				List<double[]> list = new ArrayList<>();
				for (double[] key : clusters.keySet()) {
					if (clusters.get(key)==j) {
						list.add(key);
					}
				}
				db = centroidCalculator(list);
				centroids.put(j, db);
			}
			clusters.clear();
			clusters = kmeans(readDataset.features, centroids, numberOfClusters);
		}

		// вывод
		System.out.println("Признак1\tПризнак2\tПризнак3\tПризнак4\tКластер");
		for (double[] key : clusters.keySet()) {
			for (int i = 0; i < key.length; i++) {
				System.out.print(key[i] + "\t \t \t");
			}
			System.out.print(clusters.get(key) + "\n");
		}
	}

	// вычисление центров
	public static double[] centroidCalculator(List<double[]> a) {
		int count;
		double sum;
		double[] centroids = new double[ReadDataset.numberOfFeatures];
		for (int i = 0; i < ReadDataset.numberOfFeatures; i++) {
			sum=0.0;
			count = 0;
			for(double[] x : a){
				count++;
				sum = sum + x[i];
			}
			centroids[i] = sum / count;
		}
		return centroids;
	}

	// распределение признаков по кластерам
	public static Map<double[], Integer> kmeans(List<double[]> features, Map<Integer, double[]> centroids
			, int numberOfClusters) {
		Map<double[], Integer> clusters = new HashMap<>();
		int clustersNumber = 0;
		double distance;
		for(double[] feature : features) {
			double minimum = 999999.0;
			for (int j = 0; j < numberOfClusters; j++) {
				distance = Distance.euclideanDistance(centroids.get(j), feature);
				if (distance < minimum) {
					minimum = distance;
					clustersNumber = j;
				}
			}
			clusters.put(feature, clustersNumber);
		}
		return clusters;
	}
}
