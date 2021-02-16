package customExceptions;

import org.apache.log4j.Logger;

import static org.testng.Assert.fail;

public class InvalidPairException extends Exception {

    static final Logger loggerError = Logger.getLogger("errorLogFile");

    public InvalidPairException(String fileName) {
        super(fileName);
        String errorMsg = this.getClass().getSimpleName()
                + ": File " + fileName + " doesn't have a pair to compare with.";
        loggerError.error(errorMsg);
        fail(errorMsg);
    }
}
