package utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class FileRoutine {


    public String readResourceAsString(String filePath, String fileName) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        String name = filePath.concat(fileName);
        File file = new File(Objects.requireNonNull(classLoader.getResource(name)).getFile());
        return FileUtils.readFileToString(file, "UTF-8");
    }
}
