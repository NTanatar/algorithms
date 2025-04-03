package com.nata.advent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static List<String> getFileContent(String fileName) {
        List<String> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(getFilePath(fileName)))) {
            String line;
            while ((line = br.readLine()) != null) {
                data.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    private static String getFilePath(String fileName) {
        ClassLoader classLoader = FileUtil.class.getClassLoader();
        URL resource = classLoader.getResource(fileName);

        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            return new File(resource.getFile()).getAbsolutePath();
        }
    }
}
