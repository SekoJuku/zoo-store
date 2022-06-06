package com.example.zoostore.dto.request;

import lombok.Data;

@Data
public class CustomerDetailsDtoRequest {
    private Long id;

    private String gender;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String address;

    private String country;

    private String city;
}
