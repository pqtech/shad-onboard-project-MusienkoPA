package services;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.log4j.Logger;

public class ProcessZipFile {
    final static Logger loggerDebug = Logger.getLogger("debugLogFile");
    final static Logger loggerError = Logger.getLogger("errorLogFile");

    public static void unpackFilesAB(String pathToZipFile,
                                     String pathToGoldDataFolder,
                                     String pathToOutputFilesFolder) {
        ZipFile zipFile = new ZipFile(pathToZipFile);
        loggerDebug.debug("Current zip file is valid: " + zipFile.isValidZipFile());

        try {
            // Sorting and unpacking A/B xml files
            for (int i = 1; i < zipFile.getFileHeaders().size(); i++) {
                String currentFileName = zipFile.getFileHeaders().get(i).getFileName();

                if (currentFileName.matches(".*?A[\\d]*.xml")) {
                    zipFile.extractFile(currentFileName, pathToGoldDataFolder,
                            currentFileName.replaceAll("Files/", ""));
                } else if (currentFileName.matches(".*?B[\\d]*.xml")) {
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
