package com.example.resume.repository;


import com.example.resume.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume,Integer> {

    @Query(value="SELECT r from Resume r where upper(r.firstName) = ?1 or upper(r.lastName) = ?2")
    public List<Resume> findByFirstNameOrLastNameIgnoreCase(String firstName, String lastName);

    public List<Resume> findResumeByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName,String lastName);
}
