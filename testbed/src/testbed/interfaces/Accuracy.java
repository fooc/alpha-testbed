package testbed.interfaces;

import java.util.Map;

/**
 * Interface for metrics that evaluate the correctness of rankings.
 * 
 * @author David
 * 
 */
public interface Accuracy extends Metric {

    /**
     * Evaluates given rankings against given capabilities.
     * 
     * @param trustDegrees
     *            A map of trust values, where keys represent agents and values
     *            represent their trust degrees
     * @param capabilities
     *            A map of capabilities, where keys represent agents and values
     *            represent their capabilities
     * 
     * @return An evaluation score between 0 and 1, inclusively.
     */
    public <T extends Comparable<T>> double evaluate(
	    Map<Integer, T> trustDegrees, Map<Integer, Double> capabilities);
}
