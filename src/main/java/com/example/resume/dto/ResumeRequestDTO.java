package com.example.resume.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;



@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ResumeRequestDTO {

    @Pattern(regexp = "^[a-zA-z]+\\s[a-zA-z]+$",message = "Please provide fullName (firstName lastName)")
    @NotBlank(message = "Name shouldn't be NULL OR EMPTY")
    private String name;

    @NotBlank(message = "Job Title shouldn't be NULL OR EMPTY")
    private String jobTitle;

    @NotBlank(message = "Job description shouldn't be NULL OR EMPTY")
    private String jobDescription;

    @NotBlank(message = "Company name shouldn't be NULL OR EMPTY")
    private String currentCompany;

}
