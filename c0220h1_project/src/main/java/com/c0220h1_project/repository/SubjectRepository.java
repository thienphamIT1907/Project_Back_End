package com.c0220h1_project.repository;

import com.c0220h1_project.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    Subject findSubjectBySubjectName(String name);
}
