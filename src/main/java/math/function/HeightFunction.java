package math.function;

import java.awt.*;

/**
 * Function
 */
public class HeightFunction implements FunctionInterface {

    private double lastX;
    private double lastHeight = 30;
    private double lastSpeed = 100;
    private final double k1 = 0.05;
    private final double k2 = 0.01;
    private final double m = 10;
    private double maxHeight = Double.MIN_VALUE;

    /**
     * Gets the function's title
     *
     * @return
     */
    @Override
    public String getTitle() {
        return "h(t)";
    }

    /**
     * Gets the right-most x of the function
     *
     * @return
     */
    @Override
    public double getLeftMostX() {
        return 0;
    }

    /**
     * Gets the left-most x of the function
     *
     * @return
     */
    @Override
    public double getRightMostX() {
        return 11.5;
    }

    /**
     * Gets function's value at the specified point
     *
     * @param x x to calculate function value
     * @return
     */
    @Override
    public double getValue(double x) {
        double deltaX = x - lastX;

        // calculate speed
        double speed = lastSpeed - deltaX * f();

        // calculate height
        double height = lastHeight + deltaX * speed;

        // check for max and zero height
        if (height < lastHeight && lastHeight > maxHeight) {
            maxHeight = height;

            System.out.println("Max height reached: " + maxHeight + " at t = " + x);
        }

        if (height <= 0 && lastHeight > 0) {
            System.out.println("Ground reached at t = " + x);
        }

        // set 'last' values
        lastX = x;
        lastHeight = height;
        lastSpeed = speed;

        return height;
    }

    private double f() {
        double k = lastSpeed >= 0 ? k1 : k2;

        return (m * 9.8 + k * Math.pow(lastSpeed, 2)) / m;
    }

    /**
     * Gets the function's point count
     *
     * @return
     */
    @Override
    public int getPointCount() {
        return 100;
    }

    /**
     * Gets the function's color
     *
     * @return
     */
    @Override
    public Color getColor() {
        return Color.RED;
    }

    /**
     * Does any actions the function requires
     */
    @Override
    public void initialize() {

    }

    /**
     * Checks if the function points' shapes are visible
     *
     * @return
     */
    @Override
    public boolean areShapesVisible() {
        return false;
    }

    /**
     * Checks if the function's lines are visible
     *
     * @return
     */
    @Override
    public boolean areLinesVisible() {
        return true;
    }

    /**
     * Checks if only the function's bounds need to be rendered
     *
     * @return
     */
    @Override
    public boolean onlyRenderBounds() {
        return false;
    }

    /**
     * Checks if distances between x values are even
     *
     * @return
     */
    @Override
    public boolean notEvenXIntervals() {
        return false;
    }

    /**
     * Gets the specified x value (only works if x distances are not even)
     *
     * @return
     */
    @Override
    public double getXValue() {
        return 0;
    }
}
