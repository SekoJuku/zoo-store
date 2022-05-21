package com.example.zoostore.utils;

import org.apache.tomcat.util.codec.binary.Base64;

public class ImageUtils {

    public static String compressImage(byte[] data) {
        return Base64.encodeBase64String(data);
    }

    public static byte[] decompressImage(String data) {
        return Base64.decodeBase64(data);
    }
}
