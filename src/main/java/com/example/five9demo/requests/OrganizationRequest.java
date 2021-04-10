package com.example.five9demo.requests;

import com.example.five9demo.data.Organization;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Data
public class OrganizationRequest {

    private static final ObjectMapper objectMapper = new ObjectMapper().setSerializationInclusion(
            NON_EMPTY).disable(SerializationFeature.INDENT_OUTPUT);

    @NotEmpty(message = "Invalid request: organizations list cannot be empty.")
    private List<@Valid Organization> organizations;

    public String toJson() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return this.toString();
        }
    }
}
