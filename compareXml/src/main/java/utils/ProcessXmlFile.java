package utils;

import org.apache.log4j.Logger;
import org.xmlunit.builder.Input;

import javax.xml.transform.Source;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ProcessXmlFile {
    static final Logger loggerDebug = Logger.getLogger("debugLogFile");
    static final Logger loggerError = Logger.getLogger("errorLogFile");

    public static Source getProcessedXmlFile(String pathToFile, String tagToIgnore) {
        String strFile = "";

        try {
            strFile = new String(Files.readAllBytes(Paths.get(pathToFile)),
                    StandardCharsets.UTF_8);
            loggerDebug.debug("File " + pathToFile + " was successfully read");
        } catch (IOException e) {
            e.printStackTrace();
            loggerError.error("File " + pathToFile + " wasn't opened. Error text: " + e);
        }

        String processedStrFile = "";

        // Replacing tag format for the element which should be ignored by XMLUnit but
        // text inside the element will be compared anyway as a part of a parent element
        processedStrFile = strFile.replaceAll("<" + tagToIgnore, "[" + tagToIgnore)
                .replaceAll("</" + tagToIgnore + ">", "[/" + tagToIgnore + "]");
        Source processedFile = Input.fromString(processedStrFile).build();

        return processedFile;
    }
}
