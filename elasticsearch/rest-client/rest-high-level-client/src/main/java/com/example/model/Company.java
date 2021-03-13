package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_ADDRESS = "address";

    private long companyId;
    private String name;
    private String address;
}
