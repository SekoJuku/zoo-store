package com.example.zoostore.utils.model;

import com.example.oauth2.model.User;
import com.example.zoostore.dto.request.ProfileDetailsDtoRequest;

public class CustomerUtils {
    public static User CustomerDtoToUser(ProfileDetailsDtoRequest request, User user) {
        user.setGender(request.getGender());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(request.getAddress());
        user.setCountry(request.getCountry());
        user.setCity(request.getCity());
        return user;
    }
}
