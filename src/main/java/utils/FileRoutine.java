package utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class FileRoutine {


    public String readResourceAsString(String filePath, String fileName) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        String name = filePath.concat(fileName);
        File file = new File(Objects.requireNonNull(classLoader.getResource(name)).getFile());
        return FileUtils.readFileToString(file, "UTF-8");
    }

    public void writeResourceAsString(String filePath, String fileName, String data) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(filePath + fileName);
        byte[] strToBytes = data.getBytes();
        outputStream.write(strToBytes);

        outputStream.close();
    }
}
