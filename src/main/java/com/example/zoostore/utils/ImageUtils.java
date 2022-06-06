package com.example.zoostore.utils;

import lombok.SneakyThrows;
import org.apache.tomcat.util.codec.binary.Base64;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class ImageUtils {

    public static byte[] compressImage(byte[] data) {
        return Base64.encodeBase64(data);
    }

    public static byte[] decompressImage(byte[] data) {
        return Base64.decodeBase64(data);
    }

    @SneakyThrows
    public static File byteToFile(byte[] data) {
        File file = new File("");
        try (OutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(data);
        }
        return file;
    }
}
