package main;

import action.*;
import math.function.FunctionInterface;
import math.function.HeightFunction;

import javax.swing.*;

/**
 * Main class
 */
@SuppressWarnings("Duplicates")
public class Main {

    /**
     * Entry point of the program
     *
     * @param args arguments for the program
     */
    public static void main(String[] args) {
        new Main();
    }

    // function data
    private FunctionInterface heightFunction;

    // actions
    private GraphAction eulersAction;

    /**
     * Default class constructor
     */
    private Main() {
        createData();
        showWindow();
    }

    /**
     * Creates data for functions
     */
    private void createData() {
        heightFunction = new HeightFunction();

        eulersAction = new EulerAction(heightFunction);
    }

    /**
     * Shows application's window
     */
    private void showWindow() {
        JFrame frame = new JFrame();
        frame.setTitle("L3");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setBounds(64, 64, 512, 586);
        frame.setLayout(null);
        initializeUI(frame);
        frame.setVisible(true);
    }

    /**
     * Initializes user interface
     *
     * @param frame frame to put components into
     */
    private void initializeUI(JFrame frame) {
        JButton first = new JButton("Eulerio metodas");

        first.addActionListener(eulersAction);

        frame.add(first).setBounds(0, 0, 512, 50);
    }
}
