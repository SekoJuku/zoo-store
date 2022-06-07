package com.example.zoostore.utils.model;

import com.example.zoostore.model.Image;
import com.example.zoostore.model.Product;
import com.example.zoostore.utils.ImageUtils;
import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class ImageFacade {
    @SneakyThrows
    public static Image parseResultSetToImage(ResultSet resultSet, String prefix) {
        Long id = resultSet.getLong(prefix+(!prefix.equals("")?".":"") + "id");
        String name = resultSet.getString(prefix+(!prefix.equals("")?".":"")+"name");
        String extension = resultSet.getString("extension");
        String contentType = resultSet.getString("contentType");
        byte[] data = resultSet.getBytes("data");
        return Image.builder()
                .id(id)
                .name(name)
                .extension(extension)
                .contentType(contentType)
                .data(data)
                .build();
    }
    public static void setImageIfNeeded(Product product, MultipartFile file) throws IOException {
        product.setImage(null);
        if (file != null) {
            String[] split = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
            Image image = Image.builder()
                    .name(split[0])
                    .extension(split[1])
                    .contentType(file.getContentType())
                    .data(ImageUtils.compressImage(file.getBytes()))
                    .build();
            product.setImage(image);
        }
    }
}