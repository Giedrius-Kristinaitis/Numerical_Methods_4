package action;

import graph.GraphDataProvider;
import math.Point;
import math.function.FunctionInterface;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Shows a window with a graph (data can be provided by functions or a point list)
 */
public abstract class GraphAction implements ActionListener, GraphDataProvider {

    // displayed functions
    protected List<FunctionInterface> functions = new ArrayList<FunctionInterface>();

    // displayed points
    protected List<Point> points;

    // title used when populating data from points
    protected String customPointTitle;

    // color used when populating data from points
    protected Color customPointColor;

    // shape visibility flag when populating from point list
    protected boolean areShapesVisible;

    // line visibility flag when populating from point list
    protected boolean areLinesVisible;

    // y bounds
    protected double minY;
    protected double maxY;

    // x bounds
    protected double minX;
    protected double maxX;

    /**
     * Class constructor
     *
     * @param functions displayed functions
     */
    public GraphAction(FunctionInterface... functions) {
        this.functions.addAll(Arrays.asList(functions));
    }

    /**
     * Sets point data
     *
     * @param points
     * @param customPointTitle
     * @param customPointColor
     * @param areLinesVisible
     * @param areShapesVisible
     */
    public void setPointData(List<Point> points, String customPointTitle, Color customPointColor, boolean areLinesVisible, boolean areShapesVisible, double minY, double maxY, double minX, double maxX) {
        this.points = points;
        this.customPointTitle = customPointTitle;
        this.customPointColor = customPointColor;
        this.areLinesVisible = areLinesVisible;
        this.areShapesVisible = areShapesVisible;
        this.minY = minY;
        this.maxY = maxY;
        this.minX = minX;
        this.maxX = maxX;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param event
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        for (FunctionInterface function : functions) {
            function.initialize();
        }

        showChart();
        System.out.println();
    }

    /**
     * Shows the graph chart on the screen
     */
    private void showChart() {
        // create window
        ApplicationFrame frame = new ApplicationFrame(getWindowTitle());

        // create chart
        JFreeChart chart = ChartFactory.createXYLineChart(
                getTitle(),
                getXLabel(),
                getYLabel(),
                getDataset(),
                PlotOrientation.VERTICAL,
                true,
                false,
                false
        );

        // create chart container
        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(1024, 768));
        panel.setDomainZoomable(false);
        panel.setRangeZoomable(false);

        // create renderer
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        // set line colors
        int startIndex = 0;

        if (points != null) {
            renderer.setSeriesPaint(0, customPointColor);
            renderer.setSeriesShapesVisible(0, areShapesVisible);
            renderer.setSeriesLinesVisible(0, areLinesVisible);
            startIndex = 1;
        }

        for (int i = 0; i < functions.size(); i++) {
            renderer.setSeriesPaint(startIndex + i, functions.get(i).getColor());
            renderer.setSeriesShapesVisible(startIndex + i, functions.get(i).areShapesVisible());
            renderer.setSeriesLinesVisible(startIndex + i, functions.get(i).areLinesVisible());
        }

        // apply renderer
        chart.getXYPlot().setRenderer(renderer);
        chart.getXYPlot().setDomainZeroBaselineVisible(true);
        chart.getXYPlot().setRangeZeroBaselineVisible(true);

        if (points != null) {
            chart.getXYPlot().getRangeAxis().setRange(minY - 0.1, maxY + 0.1);
            chart.getXYPlot().getDomainAxis().setRange(minX - 0.1, maxX + 0.1);
        }

        // apply chart container
        frame.setContentPane(panel);
        frame.pack();

        // show window
        RefineryUtilities.centerFrameOnScreen(frame);
        frame.setVisible(true);
    }

    /**
     * Gets the dataset
     *
     * @return
     */
    private XYDataset getDataset() {
        XYSeriesCollection dataset = new XYSeriesCollection();

        fillSeriesCollection(dataset);

        return dataset;
    }

    /**
     * Fills series collection with series
     *
     * @param collection collection to fill
     */
    private void fillSeriesCollection(XYSeriesCollection collection) {
        if (points != null) {
            XYSeries series = new XYSeries(customPointTitle);
            fillSeries(series, points);
            collection.addSeries(series);
        }

        for (FunctionInterface function : functions) {
            XYSeries series = new XYSeries(function.getTitle());
            fillSeries(series, function);
            collection.addSeries(series);
        }
    }

    /**
     * Fills xy series with point data from a function
     *
     * @param series   series to fill
     * @param function function
     */
    private void fillSeries(XYSeries series, FunctionInterface function) {
        if (function.onlyRenderBounds()) {
            series.add(function.getLeftMostX(), 0);
            series.add(function.getRightMostX(), 0);
            return;
        }

        double step = (function.getRightMostX() - function.getLeftMostX()) / (double) function.getPointCount();

        for (int i = 0; i < function.getPointCount(); i++) {
            double x = function.notEvenXIntervals() ? function.getXValue() : function.getLeftMostX() + step * i;

            if (!function.notEvenXIntervals() && x > function.getRightMostX()) {
                continue;
            }

            series.add(x, function.getValue(x));
        }
    }

    /**
     * Fills xy series with point data from a point list
     *
     * @param series series to fill
     * @param points points
     */
    private void fillSeries(XYSeries series, List<Point> points) {
        for (Point point : points) {
            series.add(point.x, point.y);
        }
    }

    /**
     * Gets chart window title
     *
     * @return
     */
    @Override
    public String getWindowTitle() {
        return "L3";
    }

    /**
     * Gets X axis label
     *
     * @return
     */
    @Override
    public String getXLabel() {
        return "";
    }

    /**
     * Gets Y axis label
     *
     * @return
     */
    @Override
    public String getYLabel() {
        return "";
    }
}
