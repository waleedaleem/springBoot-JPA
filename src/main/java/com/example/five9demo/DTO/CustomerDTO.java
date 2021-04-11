package com.example.five9demo.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

/**
 * A Data Transfer Object (DTO) that corresponds to the
 * {@link com.example.five9demo.entities.Customer} entity
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDTO {

    private static final ObjectMapper objectMapper = new ObjectMapper().setSerializationInclusion(
            NON_EMPTY).disable(SerializationFeature.INDENT_OUTPUT);

    private String name;

    private String organizationName;

    private CustomerDTO oldInstance;

    public void setName(String name) {
        this.name = name.toUpperCase();
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName.toUpperCase();
    }

    public String toJson() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return this.toString();
        }
    }
}
