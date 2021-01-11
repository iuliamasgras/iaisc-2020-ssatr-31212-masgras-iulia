package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.PN;

import java.io.IOException;
import java.io.InputStream;

public class PNReader {
    private String fileName;

    public PNReader(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Read file information from a certain location
     */
    public PN convertJsonToPetriNetwork() {

        /*create object mapper*/
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(readFile(fileName), PN.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private InputStream readFile(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        return classLoader.getResourceAsStream(fileName);
    }
}
