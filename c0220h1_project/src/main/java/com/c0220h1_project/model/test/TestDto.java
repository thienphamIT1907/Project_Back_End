package com.c0220h1_project.model.test;

import java.util.List;
import java.util.Set;

public class TestDto {
    private Integer testId;

    private String testCode;

    private String testName;

    private String grade;

    private Set<String> questions;

    private Integer subjectId;

    private List<Integer> examList;

    public Integer getTestId() {
        return testId;
    }

    public Set<String> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<String> questions) {
        this.questions = questions;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public List<Integer> getExamList() {
        return examList;
    }

    public void setExamList(List<Integer> examList) {
        this.examList = examList;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    public String getTestCode() {
        return testCode;
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

}
