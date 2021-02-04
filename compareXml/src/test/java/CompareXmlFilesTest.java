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
    final static Logger loggerError = Logger.getLogger("errorLogFile");

    private static final String PATH_TO_FILE_A = "src/data/gold_data/A.xml";
    private static final String PATH_TO_FILE_B = "src/data/output_files/B.xml";
    private static final String TAG_TO_IGNORE = "cite.query";
    private static final String PATH_TO_ZIP_FILE = "../Files.zip";
    private static final String PATH_TO_GOLD_DATA_FOLDER = "src/data/gold_data/";
    private static final String PATH_TO_OUTPUT_FILES_FOLDER = "src/data/output_files/";

    @BeforeSuite
    public void setUp() {
        ProcessZipFile.unpackFilesAB(PATH_TO_ZIP_FILE, PATH_TO_GOLD_DATA_FOLDER, PATH_TO_OUTPUT_FILES_FOLDER);
    }

    @Test
    public void compareTwoXmlFilesTest() {
        // Comparing two XML files
        Diff diff = DiffBuilder.compare(ProcessXmlFile.getProcessedXmlFile(PATH_TO_FILE_A, TAG_TO_IGNORE))
                .withTest(ProcessXmlFile.getProcessedXmlFile(PATH_TO_FILE_B, TAG_TO_IGNORE))
                .ignoreWhitespace()
                .checkForIdentical()
                .build();

        // Logging differences after replacing original tags back
        for (Difference d : diff.getDifferences()) {
            loggerError.error(d.toString().replaceAll("\\[" + TAG_TO_IGNORE, "<" + TAG_TO_IGNORE)
                    .replaceAll("\\[/" + TAG_TO_IGNORE + "\\]", "</" + TAG_TO_IGNORE + ">"));
        }

        Assert.assertFalse(diff.hasDifferences(), "Check /log/error.log file.");
    }
}
