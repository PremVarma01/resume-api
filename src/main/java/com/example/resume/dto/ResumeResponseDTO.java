package com.example.resume.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResumeResponseDTO {
    private int id;
    private String name;
    private String jobTitle;
    private String jobDescription;
    private String currentCompany;
}
