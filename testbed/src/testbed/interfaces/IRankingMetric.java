package testbed.interfaces;

import java.util.Map;

/**
 * Interface for metrics that evaluate the correctness of rankings.
 * 
 * @author David
 * 
 */
public interface IRankingMetric extends IMetric {

    /**
     * Evaluates given rankings against given capabilities.
     * 
     * @param rankings
     *            A map of rankings, where agents' ID numbers are used for keys
     *            and their respective ranks as values
     * @param capabilities
     *            A map of capabilities, where agents' ID numbers are used for
     *            keys and their respective capabilities as values
     * 
     * @return An evaluation score between 0 and 1, inclusively.
     */
    public double evaluate(Map<Integer, Integer> rankings,
	    Map<Integer, Double> capabilities);
}
