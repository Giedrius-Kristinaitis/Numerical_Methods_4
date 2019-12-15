package graph;

/**
 * Provides graph data
 */
public interface GraphDataProvider {

    /**
     * Gets chart window title
     *
     * @return
     */
    String getWindowTitle();

    /**
     * Gets chart title
     *
     * @return
     */
    String getTitle();

    /**
     * Gets X axis label
     *
     * @return
     */
    String getXLabel();

    /**
     * Gets Y axis label
     *
     * @return
     */
    String getYLabel();
}
