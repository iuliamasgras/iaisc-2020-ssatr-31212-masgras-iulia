package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class PNWriter {
    private String outFileName;

    public PNWriter(String outFileName) {
        this.outFileName = outFileName;
    }

    public void writeToFile(String line) {
        File file = new File(outFileName);
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            fileWriter = new FileWriter(file.getName(), true);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(line + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }

                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
