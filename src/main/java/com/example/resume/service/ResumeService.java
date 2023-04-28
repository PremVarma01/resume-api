package com.example.resume.service;

import com.example.resume.dto.ResumeRequestDTO;
import com.example.resume.dto.ResumeResponseDTO;
import com.example.resume.entity.Resume;
import com.example.resume.exception.ResumeNotFoundException;
import com.example.resume.exception.ResumeServiceBusinessException;
import com.example.resume.repository.ResumeRepository;
import com.example.resume.util.ValueMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ResumeService {

    private ResumeRepository resumeRepository;


    public ResumeResponseDTO createNewResume(ResumeRequestDTO resumeRequestDTO) throws ResumeServiceBusinessException {
        ResumeResponseDTO resumeResponseDTO;

        try {
            log.info("ResumeService:createNewProduct execution started.");
            Resume resume = ValueMapper.convertToEntity(resumeRequestDTO);
            log.debug("ResumeService:createNewProduct request parameters {}", ValueMapper.jsonAsString(resumeRequestDTO));

            Resume resumeResults = resumeRepository.save(resume);
            resumeResponseDTO = ValueMapper.convertToDTO(resumeResults);
            log.debug("ResumeService:createNewProduct received response from Database {}", ValueMapper.jsonAsString(resumeResponseDTO));

        } catch (Exception ex) {
            log.error("Exception occurred while persisting product to database , Exception message {}", ex.getMessage());
            throw new ResumeServiceBusinessException("Exception occurred while create a new product");
        }
        log.info("ProductService:createNewProduct execution ended.");
        return resumeResponseDTO;
    }


    public ResumeResponseDTO getResumeById(int resumeId) {
        ResumeResponseDTO resumeResponseDTO;

        try {
            log.info("ResumeService:getProductById execution started.");
            Resume resume = resumeRepository.findById(resumeId)
                    .orElseThrow(() -> new ResolutionException("Resume not found with id " + resumeId));
            resumeResponseDTO = ValueMapper.convertToDTO(resume);

            log.debug("ResumeService:getResumeById retrieving product from database for id {} {}", resumeId, ValueMapper.jsonAsString(resumeResponseDTO));

        } catch (Exception ex) {
            log.error("Exception occurred while retrieving product {} from database , Exception message {}", resumeId, ex.getMessage());
            throw new ResumeNotFoundException("Exception occurred while fetch product from Database " + resumeId);
        }

        log.info("ResumeService:getProductById execution ended.");
        return resumeResponseDTO;
    }

    public List<ResumeResponseDTO> getResumeByName(String firstName, String lastName) {
        List<ResumeResponseDTO> resumeResponseDTO;

        try {
            log.info("ResumeService:getProductById execution started.");
            List<Resume> resumeList = resumeRepository.findResumeByFirstNameIgnoreCaseAndLastNameIgnoreCase(firstName.toUpperCase(), lastName.toUpperCase());
            if (!resumeList.isEmpty()) {
                resumeResponseDTO = resumeList.stream()
                        .map(ValueMapper::convertToDTO)
                        .collect(Collectors.toList());
            } else {
                resumeList = resumeRepository.findByFirstNameOrLastNameIgnoreCase(firstName.toUpperCase(), lastName.toUpperCase());
                if (!resumeList.isEmpty()) {
                    resumeResponseDTO = resumeList.stream()
                            .map(ValueMapper::convertToDTO)
                            .collect(Collectors.toList());
                } else {
                    resumeResponseDTO = Collections.emptyList();
                }
            }
            log.debug("ResumeService:getResumeById retrieving product from database for id {} {} {}", firstName, lastName, ValueMapper.jsonAsString(resumeResponseDTO));

        } catch (Exception ex) {
            log.error("Exception occurred while retrieving product {} {] from database , Exception message  {}", firstName, lastName, ex.getMessage());
            throw new ResumeServiceBusinessException("Exception occurred while fetch product from Database " + firstName + lastName);
        }

        log.info("ResumeService:getProductById execution ended.");
        return resumeResponseDTO;
    }
//    @Cacheable(value = "product")
//    public Map<String, List<ProductResponseDTO>> getProductsByTypes() {
//        try {
//            log.info("ProductService:getProductsByTypes execution started.");
//
//            Map<String, List<ProductResponseDTO>> productsMap =
//                    productRepository.findAll().stream()
//                            .map(ValueMapper::convertToDTO)
//                            .filter(productResponseDTO -> productResponseDTO.getProductType() != null)
//                            .collect(Collectors.groupingBy(ProductResponseDTO::getProductType));
//
//            log.info("ProductService:getProductsByTypes execution ended.");
//            return productsMap;
//
//        } catch (Exception ex) {
//            log.error("Exception occurred while retrieving product grouping by type from database , Exception message {}", ex.getMessage());
//            throw new ProductServiceBusinessException("Exception occurred while fetch product from Database ");
//        }
//    }
}
