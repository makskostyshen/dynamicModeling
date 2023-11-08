package com.standing.service.impl;

import com.standing.service.api.TextFileReader;
import jakarta.inject.Singleton;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Singleton
public class TextFileReaderImpl implements TextFileReader {
    @Override
    public String read(String path) {
        try {
            // Create a FileReader to read the file
            FileReader fileReader = new FileReader(path);

            // Wrap the FileReader in a BufferedReader for efficient reading
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            StringBuffer buffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // Read and process each line of the file
                buffer.append(line);
            }

            // Close the BufferedReader and FileReader when done
            bufferedReader.close();
            fileReader.close();

            System.out.println(buffer.toString());
            return buffer.toString();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
