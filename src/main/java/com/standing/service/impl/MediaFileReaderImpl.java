package com.standing.service.impl;

import com.standing.service.api.MediaFileReader;
import jakarta.inject.Singleton;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Base64;

@Singleton
public class MediaFileReaderImpl implements MediaFileReader {
    @Override
    public String read(String path) {
        try {
            byte[] fileContent = FileUtils.readFileToByteArray(new File(path));
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
