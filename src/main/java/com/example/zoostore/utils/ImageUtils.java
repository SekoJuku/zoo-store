package com.example.zoostore.utils;

import org.apache.tomcat.util.codec.binary.Base64;

public class ImageUtils {

    public static byte[] compressImage(byte[] data) {
        return Base64.encodeBase64(data);
    }

    public static byte[] decompressImage(byte[] data) {
        return Base64.decodeBase64(data);
    }

}
