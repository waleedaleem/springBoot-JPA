package com.example.five9demo.responses;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationResponse {

    private static final ObjectMapper objectMapper = new ObjectMapper().setSerializationInclusion(
            NON_EMPTY).disable(SerializationFeature.INDENT_OUTPUT);

    private String message;
    private String messageCode;
    private String information;

    public String toJson() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return this.toString();
        }
    }
}
