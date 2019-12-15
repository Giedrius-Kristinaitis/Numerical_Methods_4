package action;

import math.function.FunctionInterface;

import java.awt.event.ActionEvent;

/**
 * Euler's method
 */
public class EulerAction extends GraphAction {

    // used function
    protected FunctionInterface function;

    /**
     * Class constructor
     * @param function
     */
    public EulerAction(FunctionInterface function) {
        super(function);
    }

    /**
     * Gets chart title
     *
     * @return
     */
    @Override
    public String getTitle() {
        return "Euler's method";
    }
}
