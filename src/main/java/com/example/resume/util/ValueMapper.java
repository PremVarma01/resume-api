package com.example.resume.util;

import com.example.resume.dto.ResumeRequestDTO;
import com.example.resume.dto.ResumeResponseDTO;
import com.example.resume.entity.Resume;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;


public class ValueMapper {

    public static Resume convertToEntity(ResumeRequestDTO resumeRequestDTO) {
        Resume resume = new Resume();
        String firstName = resumeRequestDTO.getName().split(" ")[0];
        String lastName = resumeRequestDTO.getName().split(" ")[1];
        resume.setFirstName(firstName);
        resume.setLastName(lastName);
        resume.setJobDescription(resumeRequestDTO.getJobDescription());
        resume.setJobTitle(resumeRequestDTO.getJobTitle());
        resume.setCurrentCompany(resumeRequestDTO.getCurrentCompany());
        return resume;
    }

    public static ResumeResponseDTO convertToDTO(Resume resume){
        ResumeResponseDTO resumeResponseDTO = new ResumeResponseDTO();
        resumeResponseDTO.setId(resume.getId());
        resumeResponseDTO.setName(resume.getFirstName() +" "+resume.getLastName());
        resumeResponseDTO.setJobDescription(resume.getJobDescription());
        resumeResponseDTO.setCurrentCompany(resume.getCurrentCompany());
        resumeResponseDTO.setJobTitle(resume.getJobTitle());
        return resumeResponseDTO;
    }




    public static String jsonAsString(Object obj){
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HashMap<String, Integer> createKeyValue(String resumeId, int id) {
       HashMap<String,Integer> hashMap =  new HashMap<>();
       hashMap.put(resumeId,id);
       return hashMap;
    }
}
