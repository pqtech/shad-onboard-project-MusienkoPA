package CustomDifferenceEvaluators;

import org.apache.log4j.Logger;
import org.xmlunit.diff.Comparison;
import org.xmlunit.diff.ComparisonResult;
import org.xmlunit.diff.Difference;
import org.xmlunit.diff.DifferenceEvaluator;

public class DifferenceEvaluatorWithLogger implements DifferenceEvaluator {
    static final Logger loggerError = Logger.getLogger("errorLogFile");


    private final String pathToFileA;
    private final String pathToFileB;
    private final String tagToIgnore;
    int currentDiff = 0;


    public DifferenceEvaluatorWithLogger(String pathToFileA, String pathToFileB, String tagToIgnore) {
        this.pathToFileA = pathToFileA;
        this.pathToFileB = pathToFileB;
        this.tagToIgnore = tagToIgnore;
    }

    @Override
    public ComparisonResult evaluate(Comparison comparison, ComparisonResult outcome) {

        if (outcome == ComparisonResult.DIFFERENT) {
            currentDiff++;
            loggerError.error("File [" + pathToFileA + "] and file [" + pathToFileB
                    + "], difference #"
                    + currentDiff + ":\n" + comparison.toString()
                    .replaceAll("\\[" + tagToIgnore, "<" + tagToIgnore)
                    .replaceAll("\\[/" + tagToIgnore + "\\]", "</" + tagToIgnore + ">"));
//            System.out.println(currentDiff + " IT SHOULD BE LOGGED" + pathToFileA + pathToFileB + tagToIgnore);
        }

        return outcome;
    }

}
