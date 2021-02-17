package utils;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.log4j.Logger;

public class ProcessZipFile {
    static final Logger loggerDebug = Logger.getLogger("debugLogFile");
    static final Logger loggerError = Logger.getLogger("errorLogFile");

    private static ProcessZipFile instance = new ProcessZipFile();

    private ProcessZipFile() {

    }

    public static ProcessZipFile getInstance() {
        if (instance == null) {
            instance = new ProcessZipFile();
        }
        return instance;
    }

    public void unpackFilesAB(String pathToZipFile,
                              String pathToGoldDataFolder,
                              String pathToOutputFilesFolder,
                              String sortRegexFileA,
                              String sortRegexFileB) {
        ZipFile zipFile = new ZipFile(pathToZipFile);
        loggerDebug.debug("Current zip file is valid: " + zipFile.isValidZipFile());

        try {
            // Sorting and unpacking A/B xml files
            for (int i = 1; i < zipFile.getFileHeaders().size(); i++) {
                String currentFileName = zipFile.getFileHeaders().get(i).getFileName();

                if (currentFileName.matches(sortRegexFileA)) {
                    zipFile.extractFile(currentFileName, pathToGoldDataFolder,
                            currentFileName.replaceAll("Files/", ""));
                } else if (currentFileName.matches(sortRegexFileB)) {
                    zipFile.extractFile(currentFileName, pathToOutputFilesFolder,
                            currentFileName.replaceAll("Files/", ""));
                } else {
                    loggerDebug.debug("Incorrect file name for unpacking: " + currentFileName);
                }
            }
        } catch (ZipException e) {
            e.printStackTrace();
            loggerError.error("Something's wrong. Error text: " + e);
        }
    }
}
