package services;

import org.apache.log4j.Logger;
import org.xmlunit.builder.Input;

import javax.xml.transform.Source;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ProcessXmlFile {
    final static Logger logger = Logger.getLogger(ProcessXmlFile.class);

    public static Source getProcessedXmlFile(String pathToFile, String tagToIgnoreText) {

        String regex1 = "(<" + tagToIgnoreText + ".*?>)(.*?)(</" + tagToIgnoreText + ">)";
        String regex2 = "(</" + tagToIgnoreText + "[^<]*>)([^<]*)(<" + tagToIgnoreText + ".*?>)";
        String strFile = "";

        try {
            strFile = new String(Files.readAllBytes(Paths.get(pathToFile)),
                    StandardCharsets.UTF_8);
            logger.debug("File " + pathToFile + " was successfully read");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("File " + pathToFile + " wasn't opened. Error text: " + e);
        }

        String processedStrFile = "";
        processedStrFile = strFile.replaceAll(regex1, "$1$3")
                .replaceAll(regex2, "$1$3");

        Source processedFile = Input.fromString(processedStrFile).build();

        return processedFile;
    }
}
