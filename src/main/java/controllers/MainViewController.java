package controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;


public class MainViewController {

    @FXML private TextField n;
    @FXML private TextField dt;
    @FXML private TextField lambda;
    @FXML private TextField beginX;
    @FXML private TextField endX;
    @FXML private TextField steps;

    @FXML private Button calculate;
    @FXML private Button clear;

    @FXML private LineChart<Double,Double> chart;

    private List<Double> xAxisData;
    private List<List<Double>> yAxisData;

    private CSVReader csvReader;
    private XYChart.Series series;

    private int iteration;
    private int charSteps;
    private Timeline timeline;

    public MainViewController() {

        csvReader = new CSVReader();
    }

    @FXML
    public void initialize(){

        chart.setCreateSymbols(false);
        chart.setAnimated(false);

    }

    public void calculateAction(){

        calculateData();
        setupAxisData();
        showData();

    }

    private void calculateData(){

        charSteps = Integer.valueOf(steps.getText());
        chart.getData().clear();

        try {

            Process deleteFiles = Runtime.getRuntime().exec("python3 src/main/python/delete_data_files.py");
            deleteFiles.waitFor();
            System.out.println("Files deleted");

            System.out.println("SCRIPT: Begin");
            Process pythonScript = Runtime.getRuntime().exec("python3 src/main/python/diffusion.py "+getArguments());
            pythonScript.waitFor();
            System.out.println("SCRIPT: End");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void setupAxisData(){

        try {

            xAxisData = csvReader.getXAxis();
            yAxisData = csvReader.getYAxis();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void showData(){

        iteration = 0;
        series = new XYChart.Series();
        chart.getData().add(series);
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {
            updateChart();
        }));
        timeline.setCycleCount(charSteps);
        timeline.play();

    }

    private String getArguments(){

        return " "+n.getText()+" " +dt.getText()+" " +lambda.getText()
                +" " +beginX.getText()+" " +endX.getText()+" "+steps.getText();

    }

    public void clearAction(){

        n.clear();
        dt.clear();
        lambda.clear();
        beginX.clear();
        endX.clear();
        steps.clear();

        if(timeline != null)
            timeline.stop();
        if(series != null)
            series.getData().clear();

    }

    private void updateChart(){

        series.getData().clear();
        for (int i = 0; i < Integer.valueOf(n.getText()); i++) {
            series.getData().add(new XYChart.Data(xAxisData.get(i), yAxisData.get(iteration).get(i)));
        }
        iteration++;

    }

}
