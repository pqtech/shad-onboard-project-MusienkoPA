import org.testng.annotations.DataProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DataProviders {
    protected static final String PATH_TO_GOLD_DATA_FOLDER = "src/data/gold_data/";
    protected static final String PATH_TO_OUTPUT_FILES_FOLDER = "src/data/output_files/";

    @DataProvider(name = "A/B xml files", parallel = true)
    public static Object[][] xmlFilesPathsData() {

        File[] goldFolderFiles = new File(PATH_TO_GOLD_DATA_FOLDER)
                .listFiles(((dir, name) -> name.matches("A[\\d]*.xml")));
        File[] outputFolderFiles = new File(PATH_TO_OUTPUT_FILES_FOLDER)
                .listFiles(((dir, name) -> name.matches("B[\\d]*.xml")));

        List<String> fileNamesListA = new ArrayList<>();
        List<String> fileNamesListB = new ArrayList<>();
        List<String> filesWithPairList = new ArrayList<>();

        for (File file : goldFolderFiles) {
            fileNamesListA.add(file.getName().replaceAll("A([\\d]*.xml)", "$1"));
        }

        for (File file : outputFolderFiles) {
            fileNamesListB.add(file.getName().replaceAll("B([\\d]*.xml)", "$1"));
        }

        // Matching file pairs
        for (String element : fileNamesListA) {
            if (fileNamesListB.contains(element)) {
                filesWithPairList.add(element);
            }
        }

        List<String> filesWithoutPairListA = new ArrayList<>(fileNamesListA);
        List<String> filesWithoutPairListB = new ArrayList<>(fileNamesListB);
        filesWithoutPairListA.removeAll(filesWithPairList);
        filesWithoutPairListB.removeAll(filesWithPairList);

        int objectArrLength = filesWithPairList.size() + filesWithoutPairListA.size() + filesWithoutPairListB.size();
        Object[][] result = new Object[objectArrLength][];
        int i = 0;

        // Combining file pairs with single file names into one Object
        for (String fileName : filesWithPairList) {
            result[i] = new Object[]{"A" + fileName, "B" + fileName};
            i++;
        }

        for (String fileName : filesWithoutPairListA) {
            result[i] = new Object[]{"A" + fileName, "none"};
            i++;
        }

        for (String fileName : filesWithoutPairListB) {
            result[i] = new Object[]{"none", "B" + fileName};
            i++;
        }

        return result;
    }
}
