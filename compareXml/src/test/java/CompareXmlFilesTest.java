import CustomDifferenceEvaluators.DifferenceEvaluatorWithLogger;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.Diff;
import utils.ProcessXmlFile;
import utils.ProcessZipFile;

import static org.testng.Assert.assertFalse;

public class CompareXmlFilesTest {
    private static final String TAG_TO_IGNORE = "cite.query";
    private static final String PATH_TO_ZIP_FILE = "../Files.zip";
    private static final String SORT_REGEX_FILE_A = ".*?A[\\d]*.xml";
    private static final String SORT_REGEX_FILE_B = ".*?B[\\d]*.xml";

    @BeforeSuite
    public void setUp() {
        ProcessZipFile.unpackFilesAB(PATH_TO_ZIP_FILE,
                DataProviders.PATH_TO_GOLD_DATA_FOLDER,
                DataProviders.PATH_TO_OUTPUT_FILES_FOLDER,
                SORT_REGEX_FILE_A,
                SORT_REGEX_FILE_B);
    }

    @Test(dataProviderClass = DataProviders.class, dataProvider = "A/B xml files")
    public void compareTwoXmlFilesTest(String nameFileA, String nameFileB) {
        String pathToFileA = DataProviders.PATH_TO_GOLD_DATA_FOLDER + nameFileA;
        String pathToFileB = DataProviders.PATH_TO_OUTPUT_FILES_FOLDER + nameFileB;

        // Comparing two XML files
        Diff diff = DiffBuilder.compare(ProcessXmlFile
                .getProcessedXmlFile(pathToFileA, TAG_TO_IGNORE))
                .withTest(ProcessXmlFile.getProcessedXmlFile(pathToFileB, TAG_TO_IGNORE))
                .withDifferenceEvaluator(new DifferenceEvaluatorWithLogger(pathToFileA, pathToFileB, TAG_TO_IGNORE))
                .ignoreWhitespace()
                .checkForIdentical()
                .build();

        assertFalse(diff.hasDifferences(), "There are differences between file "
                + nameFileA + " and " + nameFileB + ". Check /log/error.log file for details.");
    }
}
