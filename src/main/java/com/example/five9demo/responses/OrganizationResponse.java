package com.example.five9demo.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationResponse {
    private String message;
    private String messageCode;
    private String information;
}
