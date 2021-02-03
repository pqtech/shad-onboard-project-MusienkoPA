import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import services.ProcessXmlFile;
import services.ProcessZipFile;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.xmlunit.matchers.CompareMatcher.isIdenticalTo;

public class CompareXmlFilesTest {

    public static final String PATH_TO_FILE_A = "src/data/gold_data/A.xml";
    public static final String PATH_TO_FILE_B = "src/data/output_files/B.xml";
    public static final String TAG_TO_IGNORE_TEXT = "cite.query";
    private static final String PATH_TO_ZIP_FILE = "src/Files.zip";
    private static final String PATH_TO_GOLD_DATA_FOLDER = "src/data/gold_data/";
    private static final String PATH_TO_OUTPUT_FILES_FOLDER = "src/data/output_files/";


    @BeforeSuite
    public void setUp() {
        ProcessZipFile.unpackFilesAB(PATH_TO_ZIP_FILE, PATH_TO_GOLD_DATA_FOLDER, PATH_TO_OUTPUT_FILES_FOLDER);
    }

    @Test
    public void compareTwoXmlFilesTest() {
        assertThat(ProcessXmlFile.getProcessedXmlFile(PATH_TO_FILE_A, TAG_TO_IGNORE_TEXT),
                isIdenticalTo(ProcessXmlFile
                        .getProcessedXmlFile(PATH_TO_FILE_B, TAG_TO_IGNORE_TEXT))
                        .ignoreWhitespace());
    }
}
