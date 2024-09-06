package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import yahoofinance.YahooFinance;
import yahoofinance.Stock;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class App extends Application {

    private XYChart.Series<Number, Number> series;
    private Timer timer;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Stock Price Line Chart");

        // Create X and Y axes
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time");
        yAxis.setLabel("Stock Price");

        // Create the line chart
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Stock Price over Time");

        // Create a series for the chart
        series = new XYChart.Series<>();
        series.setName("DIA");

        lineChart.getData().add(series);

        // Create a scene and add the chart
        Scene scene = new Scene(lineChart, 800, 600);
        stage.setScene(scene);
        stage.show();

        // Start querying the stock price every 5 seconds
        startStockQuery();
    }

    private void startStockQuery() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    Stock stock = YahooFinance.get("DIA");
                    BigDecimal price = stock.getQuote().getPrice();
                    LocalDateTime now = LocalDateTime.now();
                    javafx.application.Platform.runLater(() -> {
                        series.getData().add(new XYChart.Data<>(now.getSecond(), price.doubleValue()));
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, 5000); // Query every 5 seconds
    }

    @Override
    public void stop() {
        if (timer != null) {
            timer.cancel();
        }
    }
}
