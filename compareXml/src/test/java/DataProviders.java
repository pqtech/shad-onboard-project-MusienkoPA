import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DataProviders {
    protected static final String PATH_TO_GOLD_DATA_FOLDER = "src/data/gold_data/";
    protected static final String PATH_TO_OUTPUT_FILES_FOLDER = "src/data/output_files/";
    static final Logger loggerError = Logger.getLogger("errorLogFile");

    @DataProvider(name = "A/B xml files", parallel = true)
    public static Object[][] xmlFilesPathsData() {

        File[] goldFolderFiles = new File(PATH_TO_GOLD_DATA_FOLDER)
                .listFiles(((dir, name) -> name.matches("A[\\d]*.xml")));
        File[] outputFolderFiles = new File(PATH_TO_OUTPUT_FILES_FOLDER)
                .listFiles(((dir, name) -> name.matches("B[\\d]*.xml")));

        List<String> fileNamesSetA = new ArrayList<>();
        List<String> fileNamesSetB = new ArrayList<>();
        List<String> filesWithPairSet = new ArrayList<>();

        for (File file : goldFolderFiles) {
            fileNamesSetA.add(file.getName().replaceAll("A([\\d]*.xml)", "$1"));
        }

        for (File file : outputFolderFiles) {
            fileNamesSetB.add(file.getName().replaceAll("B([\\d]*.xml)", "$1"));
        }

        for (String element : fileNamesSetA) {
            if (fileNamesSetB.contains(element)) {
                filesWithPairSet.add(element);
            }
        }

        List<String> filesWithoutPairSetA = new ArrayList<>(fileNamesSetA);
        List<String> filesWithoutPairSetB = new ArrayList<>(fileNamesSetB);

        filesWithoutPairSetA.removeAll(filesWithPairSet);
        filesWithoutPairSetB.removeAll(filesWithPairSet);

        if (!filesWithoutPairSetA.isEmpty()) {
            loggerError.error("Following A files doesn't have pairs to be compared: " + filesWithoutPairSetA);
        }

        if (!filesWithoutPairSetB.isEmpty()) {
            loggerError.error("Following B files doesn't have pairs to be compared: " + filesWithoutPairSetB);
        }

        Object[][] result = new Object[filesWithPairSet.size()][];
        for (int i = 0; i < filesWithPairSet.size(); i++) {
            result[i] = new Object[]{"A" + filesWithPairSet.get(i), "B" + filesWithPairSet.get(i)};
        }

        return result;
    }
}
