package com.xxAMIDOxx.xxSTACKSxx.menu.api.v1.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author ArathyKrishna
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCategoryRequest {
    @JsonProperty("name")
    @NotBlank
    private String name = null;

    @JsonProperty("description")
    @NotBlank
    private String description = null;
}
