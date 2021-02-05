import org.testng.annotations.DataProvider;

import java.io.File;

public class DataProviders {

    protected static final String PATH_TO_GOLD_DATA_FOLDER = "src/data/gold_data/";
    protected static final String PATH_TO_OUTPUT_FILES_FOLDER = "src/data/output_files/";

    @DataProvider(name = "A/B xml files")
    public static Object[][] xmlFilesPathsData() {

        File[] goldFolderFiles = new File(PATH_TO_GOLD_DATA_FOLDER)
                .listFiles(((dir, name) -> name.matches("A[\\d]*.xml")));
        File[] outputFolderFiles = new File(PATH_TO_OUTPUT_FILES_FOLDER)
                .listFiles(((dir, name) -> name.matches("B[\\d]*.xml")));

        int fileAmountLimit = 0;
        if (goldFolderFiles.length < outputFolderFiles.length) {
            fileAmountLimit = goldFolderFiles.length;
        } else {
            fileAmountLimit = outputFolderFiles.length;
        }

        Object[][] result = new Object[fileAmountLimit][];
        for (int i = 0; i < fileAmountLimit; i++) {
            result[i] = new Object[]{goldFolderFiles[i].getPath(), outputFolderFiles[i].getPath()};
        }

        return result;
    }
}
