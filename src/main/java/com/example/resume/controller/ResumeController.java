package com.example.resume.controller;

import com.example.resume.dto.APIResponse;
import com.example.resume.dto.ResumeRequestDTO;
import com.example.resume.dto.ResumeResponseDTO;
import com.example.resume.exception.PathVariableException;
import com.example.resume.service.ResumeService;
import com.example.resume.util.ValueMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Slf4j
@Validated
@CrossOrigin(origins = "http://localhost:3000")
public class ResumeController {


    public static final String SUCCESS = "Success";
    private ResumeService resumeService;


    @PostMapping("/createResume")
    public ResponseEntity<APIResponse> createNewResume(@RequestBody @Valid ResumeRequestDTO resumeRequestDTO) {

        log.info("ResumeController::createNewResume request body {}", ValueMapper.jsonAsString(resumeRequestDTO));

        ResumeResponseDTO resumeResponseDTO = resumeService.createNewResume(resumeRequestDTO);

        APIResponse<ResumeResponseDTO> responseDTO = APIResponse
                .<ResumeResponseDTO>builder()
                .status(SUCCESS)
                .results(resumeResponseDTO)
                .build();

        log.info("ResumeController::createNewResume response {}", ValueMapper.jsonAsString(responseDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/uploadResumeDetails")
    public ResponseEntity<APIResponse> createResume(@RequestBody @Valid ResumeRequestDTO resumeRequestDTO) {

        log.info("ResumeController::createNewResume request body {}", ValueMapper.jsonAsString(resumeRequestDTO));

        ResumeResponseDTO resumeResponseDTO = resumeService.createNewResume(resumeRequestDTO);

        APIResponse<HashMap<String, Integer>> responseDTO = APIResponse
                .<HashMap<String, Integer>>builder()
                .status(SUCCESS)
                .results(ValueMapper.createKeyValue("resumeId", resumeResponseDTO.getId()))
                .build();

        log.info("ResumeController::createNewResume response {}", ValueMapper.jsonAsString(responseDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


    @GetMapping("/getResumeById/{resumeId}")
    public ResponseEntity<?> getProduct(@PathVariable int resumeId) {

        log.info("ResumeController::getResume by id  {}", resumeId);

        ResumeResponseDTO resumeResponseDTO = resumeService.getResumeById(resumeId);
        APIResponse<ResumeResponseDTO> responseDTO = APIResponse
                .<ResumeResponseDTO>builder()
                .status(SUCCESS)
                .results(resumeResponseDTO)
                .build();

        log.info("ResumeController::getResume by id  {} response {}", resumeId, ValueMapper
                .jsonAsString(resumeResponseDTO));

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


    @GetMapping("/getResumeByName/{Name}")
    public ResponseEntity<?> getProduct(@PathVariable("Name") String Name) throws UnsupportedEncodingException {
        log.info("ResumeController::get Resume By Name  {}", Name);
        boolean nameIsValid = Pattern.matches("^[a-zA-z]+\\s[a-zA-z]+$", Name);
        if (!nameIsValid) {
            throw new PathVariableException("Please provide fullName (format: firstName lastName)");
        }
        String encodedName = URLEncoder.encode(Name, "UTF-8");
        String firstName = encodedName.replace("+", " ").split(" ")[0];
        String lastName = encodedName.replace("+", " ").split(" ")[1];
        List<ResumeResponseDTO> resumeResponseDTO = resumeService.getResumeByName(firstName, lastName);
        APIResponse<List<ResumeResponseDTO>> responseDTO = APIResponse
                .<List<ResumeResponseDTO>>builder()
                .status(SUCCESS)
                .results(resumeResponseDTO)
                .build();

        log.info("ResumeController::get Resume By Name {} response {}", Name, ValueMapper
                .jsonAsString(resumeResponseDTO));

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

}
