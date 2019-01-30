package controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CSVReader {

    private List<List<Double>> yAxis;

    private String xFile = "src/main/data/x.csv";
    private String yFile = "src/main/data/y.csv";
    private BufferedReader bufferedReader;
    private String cvsSplit = ",";
    private String line;


    public List<Double> getXAxis() throws IOException {

        bufferedReader = new BufferedReader(new FileReader(xFile));
        line = bufferedReader.readLine();
        String [] xData = line.split(cvsSplit);
        return Arrays.asList(fromStringToDoubleArray(xData));

    }

    public List<List<Double>> getYAxis() throws IOException {
        yAxis = new LinkedList<>();
        String [] data;
        bufferedReader = new BufferedReader(new FileReader(yFile));
        while ((line = bufferedReader.readLine()) != null){
            data = line.split(cvsSplit);
            yAxis.add(Arrays.asList(fromStringToDoubleArray(data)));
        }
        return yAxis;
    }

    private Double[] fromStringToDoubleArray(String[] array){
        return Arrays.stream(array).map(Double::valueOf).toArray(Double[]::new);
    }

}
