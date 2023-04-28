package com.example.resume.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CustomName {
    @Pattern(regexp = "^[a-zA-z]+\\+[a-zA-z]+$",message = "Please provide fullName (firstName+lastName)")
    @NotBlank(message = "Full shouldn't be NULL OR EMPTY")
    private String Name;
}
