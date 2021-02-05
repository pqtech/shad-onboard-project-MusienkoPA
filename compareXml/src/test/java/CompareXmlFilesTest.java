import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.Diff;
import org.xmlunit.diff.Difference;
import services.ProcessXmlFile;
import services.ProcessZipFile;

public class CompareXmlFilesTest {
    static final Logger loggerError = Logger.getLogger("errorLogFile");

    private static final String TAG_TO_IGNORE = "cite.query";
    private static final String PATH_TO_ZIP_FILE = "../Files.zip";

    @BeforeSuite
    public void setUp() {
        ProcessZipFile.unpackFilesAB(PATH_TO_ZIP_FILE,
                DataProviders.PATH_TO_GOLD_DATA_FOLDER,
                DataProviders.PATH_TO_OUTPUT_FILES_FOLDER);
    }

    @Test(dataProviderClass = DataProviders.class, dataProvider = "A/B xml files")
    public void compareTwoXmlFilesTest(String pathToFileA, String pathToFileB) {
        // Comparing two XML files
        Diff diff = DiffBuilder.compare(ProcessXmlFile
                .getProcessedXmlFile(pathToFileA, TAG_TO_IGNORE))
                .withTest(ProcessXmlFile.getProcessedXmlFile(pathToFileB, TAG_TO_IGNORE))
                .ignoreWhitespace()
                .checkForIdentical()
                .build();

        // Logging differences after replacing original tags back
        int currentDiff = 0;
        for (Difference d : diff.getDifferences()) {
            currentDiff++;
            loggerError.error("File [" + pathToFileA + "] and file [" + pathToFileB
                    + "], difference #"
                    + currentDiff + ":\n" + d.toString()
                    .replaceAll("\\[" + TAG_TO_IGNORE, "<" + TAG_TO_IGNORE)
                    .replaceAll("\\[/" + TAG_TO_IGNORE + "\\]", "</" + TAG_TO_IGNORE + ">"));
        }

        Assert.assertFalse(diff.hasDifferences(), "Check /log/error.log file.");
    }
}
