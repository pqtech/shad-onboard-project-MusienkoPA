import org.testng.annotations.Test;
import services.ProcessXmlFile;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.xmlunit.matchers.CompareMatcher.isIdenticalTo;

public class CompareXmlFilesTest {

    public static final String PATH_TO_FILE_A = "src/data/gold_data/A.xml";
    public static final String PATH_TO_FILE_B = "src/data/output_files/B.xml";
    public static final String TAG_TO_IGNORE_TEXT = "cite.query";

    @Test
    public void compareTwoXmlFilesTest() {
        assertThat(ProcessXmlFile.getProcessedXmlFile(PATH_TO_FILE_A, TAG_TO_IGNORE_TEXT),
                isIdenticalTo(ProcessXmlFile
                        .getProcessedXmlFile(PATH_TO_FILE_B, TAG_TO_IGNORE_TEXT))
                        .ignoreWhitespace());
    }
}
